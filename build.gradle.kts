import myaa.subkt.ass.*
import myaa.subkt.tasks.*
import myaa.subkt.tasks.Mux.*
import myaa.subkt.tasks.Nyaa.*
import java.awt.Color
import java.time.*

plugins {
    id("myaa.subkt")
}

subs {
    readProperties("sub.properties")
    episodes(getList("episodes"))

    merge {
        from(get("dialogue"))
        from(get("ED"))

        from(get("IS_1"))
        from(get("IS_2"))

        from(get("TS"))

        out(get("mergedname"))
    }

    chapters {
        from(merge.item())
        chapterMarker("chapter")
    }


    mux {
        title(get("title"))

        from(get("premux")) {
            includeChapters(false)
			attachments { include(false) }
        }

		from(merge.item()) {
			tracks {
				lang("eng")
                name(get("group"))
				default(true)
				forced(false)
				compression(CompressionType.ZLIB)
			}
		}

        chapters(chapters.item()) { lang("eng") }

        attach(get("fonts")) {
            includeExtensions("ttf", "otf")
        }

        out(get("muxout"))
    }
}
