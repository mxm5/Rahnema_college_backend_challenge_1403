package service;

import entities.CommandStereoType;
import entities.Result;

import java.util.Objects;

public class KeysCMD extends CommandStereoType {
    private int pageNumber = 1;
    private int limit = 10;

    public KeysCMD(String[] commandChain) {
        super(commandChain);
    }

    private String regex;

    @Override
    public Result runCommand() {
        Result result = getSelectedDatabase().searchPaginatedByRegex(regex, pageNumber, limit);
        result.getFoundResults().forEach(System.out::println);
        return result;
    }

    @Override
    protected void runValidateChecksAndSetParams() {
        checkForLengthMoreThan(6);
        checkForLengthLessThan(2);
        setSearchRegex();
        setPageData();
    }

    private void setPageData() {
        try {
            if (Objects.equals(commandChain[2].toLowerCase(), "page")) {
                this.pageNumber = Integer.parseInt(commandChain[3]);
            }
        } catch (Exception e) {
            new Result("pages could not be set using default page 1");
            this.pageNumber = 1;
        }
        try {
            if (Objects.equals(commandChain[4].toLowerCase(), "limit")) {
                this.limit = Integer.parseInt(commandChain[5]);
            }
        } catch (Exception e) {
             new Result("pages could not be set using default page 1");
             this.limit = 10;
        }

    }

    private void setSearchRegex() {
        try {
            this.regex = commandChain[1];
        } catch (Exception e) {
            throw new RuntimeException("could not register the regex for searching");
        }
    }
}
