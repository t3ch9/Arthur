package com.codebrig.omnisrc.observe.filter

/**
 * Match by the simple name
 *
 * @version 0.3
 * @since 0.2
 * @author <a href="mailto:brandon.fergerson@codebrig.com">Brandon Fergerson</a>
 */
class SimpleNameFilter extends MultiFilter {

    SimpleNameFilter(String name) {
        super(MatchStyle.ALL, new TokenFilter(name), new TypeFilter("SimpleName"))
    }
}