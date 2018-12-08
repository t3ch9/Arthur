package com.codebrig.omnisrc.observe.filter

import com.codebrig.omnisrc.SourceNode
import com.codebrig.omnisrc.SourceNodeFilter

/**
 * Match by the token
 *
 * @version 0.3
 * @since 0.2
 * @author <a href="mailto:brandon.fergerson@codebrig.com">Brandon Fergerson</a>
 */
class TokenFilter extends SourceNodeFilter<TokenFilter, String> {

    TokenFilter(String... values) {
        accept(values)
    }

    @Override
    boolean evaluate(SourceNode node) {
        if (node != null) {
            return evaluateProperty(node.token)
        }
        return false
    }
}
