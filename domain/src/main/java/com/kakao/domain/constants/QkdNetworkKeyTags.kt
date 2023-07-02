package com.kakao.domain.constants

object QkdNetworkKeyTags {

    object HEADER {
        const val AUTHORIZATION = "authorization"
    }

    object QueryParam {
        const val QUERY = "query"
        const val SORT = "sort"
        const val PAGE = "page"
        const val SIZE = "size"
    }

    object Response {
        const val META = "meta"
        const val DOCUMENTS = "documents"

        const val TOTAL_COUNT = "total_count"
        const val PAGEABLE_COUNT = "pageable_count"
        const val IS_END = "is_end"

        const val TITLE = "title"
        const val URL = "url"
        const val DATETIME = "datetime"
        const val PLAY_TIME = "play_time"
        const val THUMBNAIL = "thumbnail"
        const val AUTHOR = "author"

        const val COLLECTION = "collection"
        const val THUMBNAIL_URL = "thumbnail_url"
        const val IMAGE_URL = "image_url"
        const val WIDTH = "width"
        const val HEIGHT = "height"
        const val DISPLAY_SITENAME = "display_sitename"
        const val DOC_URL = "doc_url"
    }
}