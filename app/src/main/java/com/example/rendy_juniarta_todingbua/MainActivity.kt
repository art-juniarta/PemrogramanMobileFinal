package com.example.rendy_juniarta_todingbua

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Switch
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rendy_juniarta_todingbua.db.DataManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var db = DataManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Define switch button
        val btn = findViewById<Switch>(R.id.switch1)

        btn.setOnCheckedChangeListener { _, isChecked ->

        //When the switch is checked, change background to black. Otherwise white. Also change the color of the text: to white when bg is black, vice versa.
            if (btn.isChecked) {

                findViewById<ConstraintLayout>(R.id.consLayout).setBackgroundColor(Color.parseColor("#FF000000"))
                btn.text = "Disable dark mode"
                btn.setTextColor(Color.parseColor("#FFFFFFFF") )

            } else {
                findViewById<ConstraintLayout>(R.id.consLayout).setBackgroundColor(Color.parseColor("#FFFFFFFF"))
                btn.text = "Enable dark mode"
                btn.setTextColor(Color.parseColor("#FF000000") )
            }
        }



       //Display items in recycler view using adapter
        val bookListItems = ArrayList<BookClass>()
        val adapter = BookAdapter(bookListItems, this)
        val layoutManager = LinearLayoutManager(this)

        recyclerViewId.layoutManager = layoutManager
        recyclerViewId.adapter = adapter

        var bookList: ArrayList<BookClass> = db.searchAll()
        bookList.reverse()

        for (c in bookList.iterator()) {
            val book = BookClass()
            book.bookName = c.bookName
            book.author =  c.author
            book.page =  c.page
            book.id = c.id

            bookListItems.add(book)
        }

        adapter.notifyDataSetChanged()
    }


// Book_menu layout
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.book_menu, menu)
        return true
    }
    //When add book button clicked, start AddBookActivity
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        val b = when(id)
        {
            R.id.settings ->
            {
                val intent= Intent(this,AddBookActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return b
    }
}