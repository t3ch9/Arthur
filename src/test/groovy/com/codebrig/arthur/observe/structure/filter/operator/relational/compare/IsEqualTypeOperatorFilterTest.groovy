package com.codebrig.arthur.observe.structure.filter.operator.relational.compare

import com.codebrig.arthur.ArthurTest
import com.codebrig.arthur.SourceLanguage
import com.codebrig.arthur.observe.structure.filter.FunctionFilter
import com.codebrig.arthur.observe.structure.filter.MultiFilter
import com.codebrig.arthur.observe.structure.filter.NameFilter
import gopkg.in.bblfsh.sdk.v1.protocol.generated.Encoding
import org.junit.Test

import static org.junit.Assert.*

class IsEqualTypeOperatorFilterTest extends ArthurTest {

    @Test
    void isEqualTypeOperator_Javascript() {
        assertIsEqualTypeOperatorPresent(new File("src/test/resources/same/operators/Operators.js"))
    }

    @Test
    void isEqualTypeOperator_Php() {
        assertIsEqualTypeOperatorPresent(new File("src/test/resources/same/operators/Operators.php"))
    }

    @Test
    void isEqualTypeOperator_Ruby() {
        assertIsEqualTypeOperatorPresent(new File("src/test/resources/same/operators/Operators.rb"))
    }

    private static void assertIsEqualTypeOperatorPresent(File file) {
        def language = SourceLanguage.getSourceLanguage(file)
        def resp = client.parse(file.name, file.text, language.babelfishName, Encoding.UTF8$.MODULE$)

        def foundEqualTypeOperator = false
        def functionFilter = new FunctionFilter()
        def nameFilter = new NameFilter("isEqualTypeOperator()")
        MultiFilter.matchAll(functionFilter, nameFilter).getFilteredNodes(language, resp.uast).each {
            assertEquals("isEqualTypeOperator()", it.name)

            new IsEqualTypeOperatorFilter().getFilteredNodes(it).each {
                assertFalse(foundEqualTypeOperator)
                if (!it.token.isEmpty()) assertEquals("===", it.token)
                foundEqualTypeOperator = true
            }
        }
        assertTrue(foundEqualTypeOperator)
    }
}