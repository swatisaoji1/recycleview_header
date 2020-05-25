package com.example.recycleviewwithheader

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * Pieces for RecycleView.
 * A recycleView
 * A custom Adapter that extends  RecyclerView.Adapter @see RecyclerAdapter for example
 *
 */
class MainActivity : AppCompatActivity() {

    private lateinit var recycleView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycleView = findViewById(R.id.recyclerview)

        recycleView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = RecyclerAdapter(createListOfItems())

            // use the size of the header for this offset : -80
            addItemDecoration(ItemDecorator(-80))
        }
    }

    private fun createListOfItems(): List<ItemData> {
        return listOf(
                HeaderData("25 Jan", "25 c", 1),
                NonHeaderData("Some test event", "Subtitle of the test event", 1),
                NonHeaderData("Some test event", "Subtitle of the test event", 1),
                NonHeaderData("Some test event", "Subtitle of the test event", 1),
                NonHeaderData("Some test event", "Subtitle of the test event", 1),
                NonHeaderData("Some test event", "Subtitle of the test event", 1),
                HeaderData("26 Jan", "27 c", 1),
                NonHeaderData("Some test event", "Subtitle of the test event", 1),
                NonHeaderData("Some test event", "Subtitle of the test event", 1),
                NonHeaderData("Some test event", "Subtitle of the test event", 1),
                HeaderData("26 Jan", "27 c", 1),
                NonHeaderData("Some test event", "Subtitle of the test event", 1),
                NonHeaderData("Some test event", "Subtitle of the test event", 1),
                NonHeaderData("Some test event", "Subtitle of the test event", 1),
                HeaderData("26 Jan", "27 c", 1),
                NonHeaderData("Some test event", "Subtitle of the test event", 1),
                NonHeaderData("Some test event", "Subtitle of the test event", 1),
                NonHeaderData("Some test event", "Subtitle of the test event", 1),
                HeaderData("26 Jan", "27 c", 1),
                NonHeaderData("Some test event", "Subtitle of the test event", 1),
                NonHeaderData("Some test event", "Subtitle of the test event", 1),
                NonHeaderData("Some test event", "Subtitle of the test event", 1))
    }

    class RecyclerAdapter(private val items: List<ItemData>) : RecyclerView.Adapter<CalendarItemHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarItemHolder = when (viewType) {
            ItemType.HEADER.ordinal ->
                CalendarItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.header_item, parent, false))
            else ->
                CalendarItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false))
        }

        override fun getItemCount(): Int = items.size

        override fun getItemViewType(position: Int): Int {
            val itemType = items[position].getType().ordinal
            if (itemType == ItemType.EVENT.ordinal && items[position - 1].getType().ordinal == ItemType.HEADER.ordinal)  {
                return ItemType.FIRST_EVENT.ordinal
            }

            return (items[position].getType().ordinal)
        }

        override fun onBindViewHolder(holder: CalendarItemHolder, position: Int) {
            holder.bindItem(items[position])
        }
    }
}

class CalendarItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindItem(calendarData: ItemData) {
        if (calendarData.getType() == ItemType.HEADER) {
            val headerData = calendarData as HeaderData
            itemView.findViewById<TextView>(R.id.header_title).apply {
                text = headerData.date
            }

            itemView.findViewById<TextView>(R.id.header_subtitle).apply {
                text = headerData.temperature
            }

        } else {
            val eventData = calendarData as NonHeaderData
            itemView.findViewById<TextView>(R.id.event_title).apply {
                text = eventData.title
            }

            itemView.findViewById<TextView>(R.id.event_subtitle).apply {
                text = eventData.subtitle
            }
        }
    }
}

class ItemDecorator(private val space: Int) : ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        // get the view type
        // get the view type
        val viewType: Int = parent.adapter?.getItemViewType(position) ?: ItemType.EVENT.ordinal
        if (viewType == ItemType.FIRST_EVENT.ordinal) outRect.top = space
    }
}
