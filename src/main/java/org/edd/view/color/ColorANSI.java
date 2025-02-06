package org.edd.view.color;

public class ColorANSI {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    protected String formatSuccess(String text){
        return ANSI_GREEN + text + ColorANSI.ANSI_RESET;
    }

    protected String formatNotice(String text){
        return ANSI_YELLOW + text + ColorANSI.ANSI_RESET;
    }

    protected String formatError(String text){
        return ANSI_RED + text + ColorANSI.ANSI_RESET;
    }

    // TODO: another option
    protected void printSuccess(String text){
        System.out.println(ANSI_GREEN + text + ColorANSI.ANSI_RESET);
    }
}
