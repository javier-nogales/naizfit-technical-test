package com.naizfit.app.interfaceapi.routes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Route {
	
    private final String method;
    private final Handler handler;
    private final Pattern regex;
    private final List<String> variableNames;

    // ───── CONSTRUCTORS ────────────────────────────────────────────────────
    public Route(final String method,
            	 final String pathPattern,
            	 final SimpleHandler simpleHandler) {
    	
    	this(method, pathPattern, (req, res, pathVars) -> simpleHandler.handle(req, res));
    }
    
    public Route(final String method, 
    			 final String pathPattern, 
    			 final Handler handler) {
    	
        this.method = method;
        this.handler = handler;
        this.variableNames = new ArrayList<>();
        this.regex = Pattern.compile(convertPattern(pathPattern, variableNames));
    }

    // ────────────────────────────────────────────────────────────────────────
    /**
     * Try to match this route against given method and path. Returns Matcher if matches, null otherwise.
     */
    public Matcher match(String requestMethod, String path) {
    	
        if (!method.equalsIgnoreCase(requestMethod)) return null;
        Matcher m = regex.matcher(path);
        return m.matches() ? m : null;
    }

    /**
     * Extract path variables from a successful Matcher.
     */
    public Map<String,String> extractPathVariables(Matcher matcher) {
    	
        Map<String,String> vars = new HashMap<>();
        
        for (int i = 0; i < variableNames.size(); i++) {
            String name = variableNames.get(i);
            String value = matcher.group(i + 1);
            vars.put(name, value);
        }
        
        return vars;
    }

    public Handler getHandler() {
        return handler;
    }

    /**
     * Convert a pathPatern like "/items/:id/details" into a regex, capturing variable names.
     */
    private String convertPattern(String pattern, List<String> varNames) {
    	
        StringBuilder regex = new StringBuilder();
        String[] segments = pattern.split("/");
        
        for (String seg : segments) {
        	
            if (seg.isEmpty()) continue;
            regex.append("/");
            
            if (seg.startsWith(":")) {
                String var = seg.substring(1);
                varNames.add(var);
                regex.append("([^/]+)");
                
            } else {
                regex.append(Pattern.quote(seg));
            }
        }
        
        // allow optional trailing slash
        regex.append("/?");
        return "^" + regex + "$";
    }
}
