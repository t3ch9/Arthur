package com.codebrig.arthur.observe.structure.filter.conditional

import com.codebrig.arthur.ArthurTest
import com.codebrig.arthur.SourceLanguage
import com.codebrig.arthur.observe.structure.filter.FunctionFilter
import com.codebrig.arthur.observe.structure.filter.MultiFilter
import com.codebrig.arthur.observe.structure.filter.NameFilter
import gopkg.in.bblfsh.sdk.v1.protocol.generated.Encoding
import org.junit.Test

import static org.junit.Assert.*

class SwitchCaseConditionalFilterTest extends ArthurTest {

    @Test
    void switchCaseConditional_Go() {
        assertSwitchCaseConditionalPresent(new File("src/test/resources/same/conditionals/Conditionals.go"))
    }

    @Test
    void switchCaseConditional_Java() {
        assertSwitchCaseConditionalPresent(new File("src/test/resources/same/conditionals/Conditionals.java"),
                "Conditionals.")
    }

    @Test
    void switchCaseConditional_Javascript() {
        assertSwitchCaseConditionalPresent(new File("src/test/resources/same/conditionals/Conditionals.js"))
    }

    @Test
    void switchCaseConditional_Php() {
        assertSwitchCaseConditionalPresent(new File("src/test/resources/same/conditionals/Conditionals.php"))
    }

    @Test
    void switchCaseConditional_CSharp() {
        assertSwitchCaseConditionalPresent(new File("src/test/resources/same/conditionals/Conditionals.cs"))
    }

    @Test
    void switchCaseConditional_CPlusPlus() {
        assertSwitchCaseConditionalPresent(new File("src/test/resources/same/conditionals/Conditionals.cpp"))
    }

    @Test
    void switchCaseConditional_Bash() {
        assertSwitchCaseConditionalPresent(new File("src/test/resources/same/conditionals/Conditionals.sh"))
    }

    private static void assertSwitchCaseConditionalPresent(File file) {
        assertSwitchCaseConditionalPresent(file, "")
    }

    @Test
    void switchCaseConditional_Ruby() {
        assertSwitchCaseConditionalPresent(new File("src/test/resources/same/conditionals/Conditionals.rb"))
    }

    private static void assertSwitchCaseConditionalPresent(File file, String qualifiedName) {
        def language = SourceLanguage.getSourceLanguage(file)
        def resp = client.parse(file.name, file.text, language.babelfishName, Encoding.UTF8$.MODULE$)

        def foundSwitchCaseConditional = false
        def functionFilter = new FunctionFilter()
        def nameFilter = new NameFilter(qualifiedName + "switchCaseConditional()")
        MultiFilter.matchAll(functionFilter, nameFilter).getFilteredNodes(language, resp.uast).each {
            assertEquals(qualifiedName + "switchCaseConditional()", it.name)

            new SwitchCaseConditionalFilter().getFilteredNodes(it).each {
                assertFalse(foundSwitchCaseConditional)
                foundSwitchCaseConditional = true
            }
        }
        assertTrue(foundSwitchCaseConditional)
    }
}