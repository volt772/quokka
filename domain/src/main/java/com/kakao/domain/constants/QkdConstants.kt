package com.kakao.domain.constants

object QkdConstants {

    object Uri {

        const val BASE = "https://dapi.kakao.com"
    }

    object ApiUri {

        private const val URL_BASE = "/v2/search"

        const val URL_SEARCH_IMG = "$URL_BASE/image"

        const val URL_SEARCH_VCLIP = "$URL_BASE/vclip"
    }

    object DataSource {

        const val PAGING_LOAD_SIZE = 10

        const val PAGING_NETWORK_SIZE = 10

        const val PAGING_NETWORK_SORT = "recency"
    }

}