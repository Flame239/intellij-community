// Copyright 2000-2024 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package org.jetbrains.plugins.groovy.lang.formatter;

import com.intellij.psi.codeStyle.CommonCodeStyleSettings;
import org.jetbrains.plugins.groovy.GroovyLanguage;

/**
 * @author Max Medvedev
 */
public class WrappingTest extends GroovyFormatterTestCase {
  @Override
  protected void setUp() throws Exception {
    super.setUp();
    myTempSettings.setRightMargin(GroovyLanguage.INSTANCE, 10);
  }

  public void testWrapChainedMethodCalls() {
    getGroovySettings().METHOD_CALL_CHAIN_WRAP = CommonCodeStyleSettings.WRAP_ALWAYS;

    checkFormatting("""
                      foo().barbar().abcd()
                      """, """
                      foo()
                          .barbar()
                          .abcd()
                      """);
  }

  public void testWrapChainedMethodCallsWithDotAfter() {
    getGroovySettings().METHOD_CALL_CHAIN_WRAP = CommonCodeStyleSettings.WRAP_ALWAYS;
    getGroovyCustomSettings().WRAP_CHAIN_CALLS_AFTER_DOT = true;

    checkFormatting("""
                      foo().barbar().abcd()
                      """, """
                      foo().
                          barbar().
                          abcd()
                      """);
  }

  public void testWrappingInsideGString0() {
    checkFormatting("""
                      "abcdefghij${a+b}"
                      """, """
                      "abcdefghij${a + b}"
                      """);
  }

  public void testWrappingInsideGString1() {
    checkFormatting("""
                      ""\"abcdefghij${a+b}""\"
                      """, """
                      ""\"abcdefghij${a + b}""\"
                      """);
  }

  public void testWrappingInsideGString2() {
    getGroovySettings().KEEP_SIMPLE_LAMBDAS_IN_ONE_LINE = false;
    checkFormatting("""
                      ""\"abcdefghij${a+b}""\"
                      """, """
                      ""\"abcdefghij${
                        a + b
                      }""\"
                      """);
  }

  public void testWrappingInsideGString3() {
    checkFormatting("""
                      ""\"text with ${foo}""\"
                      """, """
                      ""\"text with ${foo}""\"
                      """);
  }

  public void testWrappingInsideGString4() {
    getGroovySettings().KEEP_SIMPLE_LAMBDAS_IN_ONE_LINE = false;
    checkFormatting("""
                      ""\"text with ${foooo}""\"
                      """, """
                      ""\"text with ${
                        foooo
                      }""\"
                      """);
  }

  public void testWrappingInsideGString5() {
    getGroovySettings().KEEP_SIMPLE_LAMBDAS_IN_ONE_LINE = false;
    checkFormatting("""
                      ""\"text with ${foo}""\"
                      """, """
                      ""\"text with ${
                        foo}""\"
                      """);
  }

  public void testWrapArgumentList() {
    getGroovySettings().CALL_PARAMETERS_WRAP = CommonCodeStyleSettings.WRAP_AS_NEEDED;
    checkFormatting("""
                      printxxxxxx(2)
                      """, """
                      printxxxxxx(
                          2)
                      """);
  }

  public void testWrapOneLineClosure() {
    checkFormatting("""
                      ""\"def barbar = {foo}""\"
                      """, """
                      ""\"def barbar = {foo}""\"
                      """);
  }

  public void testWrapOneLineClosure2() {
    getGroovySettings().KEEP_SIMPLE_LAMBDAS_IN_ONE_LINE = false;
    checkFormatting("""
                      def barbarbar = {foo}
                      """, """
                      def barbarbar = {
                        foo
                      }
                      """);
  }
}
