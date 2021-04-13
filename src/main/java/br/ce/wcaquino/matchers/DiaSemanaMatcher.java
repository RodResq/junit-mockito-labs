package br.ce.wcaquino.matchers;


import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class DiaSemanaMatcher extends TypeSafeMatcher {

    protected boolean matchesSafely(Object o) {
        return false;
    }

    public void describeTo(Description description) {

    }
}
