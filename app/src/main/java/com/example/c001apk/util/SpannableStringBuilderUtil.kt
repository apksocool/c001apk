package com.example.c001apk.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.URLSpan
import com.example.c001apk.view.CenteredImageSpan
import com.example.c001apk.view.CenteredImageSpan1
import com.example.c001apk.view.MyURLSpan
import java.util.regex.Pattern

object SpannableStringBuilderUtil {

    fun setEmoji(mContext: Context, text: String, size: Int): SpannableStringBuilder {
        val builder = SpannableStringBuilder(text)
        val pattern = Pattern.compile("\\[[^\\]]+\\]")
        val matcher = pattern.matcher(builder)
        while (matcher.find()) {
            val group = matcher.group()
            if (EmojiUtil.getEmoji(group) != -1) {
                val emoji: Drawable =
                    mContext.getDrawable(EmojiUtil.getEmoji(group))!!
                emoji.setBounds(
                    0,
                    0,
                    size,
                    size
                )
                val imageSpan = CenteredImageSpan(emoji, size)
                builder.setSpan(
                    imageSpan,
                    matcher.start(),
                    matcher.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        return builder
    }

    private var position = 0
    private var uid = ""
    fun setData(position: Int, uid: String) {
        this.position = position
        this.uid = uid
    }

    var isReturn = false
    var isColor = false

    fun setText(
        mContext: Context,
        text: String,
        size: Int,
        imgList: List<String>?
    ): SpannableStringBuilder {
        val mess = Html.fromHtml(
            text.replace("\n", " <br/>") + "\u3000",
            Html.FROM_HTML_MODE_COMPACT
        )
        val builder = SpannableStringBuilder(mess)
        val pattern = Pattern.compile("\\[[^\\]]+\\]")
        val matcher = pattern.matcher(builder)
        val urls = builder.getSpans(
            0, mess.length,
            URLSpan::class.java
        )
        urls.forEach {
            val myURLSpan = MyURLSpan(mContext, it.url, imgList)
            myURLSpan.setData(position, uid)
            myURLSpan.isColor = isColor
            myURLSpan.isReturn = isReturn
            val start = builder.getSpanStart(it)
            val end = builder.getSpanEnd(it)
            val flags = builder.getSpanFlags(it)
            builder.setSpan(myURLSpan, start, end, flags)
            builder.removeSpan(it)
        }
        while (matcher.find()) {
            val group = matcher.group()
            if (EmojiUtil.getEmoji(group) != -1) {
                val emoji: Drawable =
                    mContext.getDrawable(EmojiUtil.getEmoji(group))!!
                emoji.setBounds(
                    0,
                    0,
                    size,
                    size
                )
                val imageSpan = CenteredImageSpan(emoji, size)
                builder.setSpan(
                    imageSpan,
                    matcher.start(),
                    matcher.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        return builder
    }

    fun setReply(
        mContext: Context,
        text: String,
        size: Int,
        imgList: List<String>?
    ): SpannableStringBuilder {
        val mess = Html.fromHtml(
            text.replace("\n", " <br/>") + "\u3000",
            Html.FROM_HTML_MODE_COMPACT
        )
        val builder = SpannableStringBuilder(mess)
        val pattern = Pattern.compile("\\[[^\\]]+\\]")
        val matcher = pattern.matcher(builder)
        val urls = builder.getSpans(
            0, mess.length,
            URLSpan::class.java
        )
        urls.forEach {
            val myURLSpan = MyURLSpan(mContext, it.url, imgList)
            myURLSpan.setData(position, uid)
            myURLSpan.isColor = isColor
            val start = builder.getSpanStart(it)
            val end = builder.getSpanEnd(it)
            val flags = builder.getSpanFlags(it)
            builder.setSpan(myURLSpan, start, end, flags)
            builder.removeSpan(it)
        }
        while (matcher.find()) {
            val group = matcher.group()
            if (EmojiUtil.getEmoji(group) != -1) {
                val emoji: Drawable =
                    mContext.getDrawable(EmojiUtil.getEmoji(group))!!
                if (group == "[楼主]" || group == "[层主]" || group == "[置顶]")
                    emoji.setBounds(
                        0,
                        0,
                        size * 2,
                        size
                    )
                else
                    emoji.setBounds(
                        0,
                        0,
                        (size * 1.3).toInt(),
                        (size * 1.3).toInt()
                    )
                val imageSpan = CenteredImageSpan1(emoji)
                builder.setSpan(
                    imageSpan,
                    matcher.start(),
                    matcher.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        return builder
    }

}