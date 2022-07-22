package com.example.contextmenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val data = arrayOf(
        "List 1", "List 2", "List 3", "List 4", "List 5"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)

        list1.adapter = adapter
        list1.setOnItemClickListener { parent, view, position, id ->
            textView.text = "ListView Position : ${data[position]}"
        }
        //ContextMenu를 View에 등록한다.
        registerForContextMenu(textView)
        registerForContextMenu(list1)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)

        //길게 누른 View의 ID로 분기한다.
        when(v?.id){
            R.id.textView->{
                menu?.setHeaderTitle("텍스트 뷰의 메뉴")
                menuInflater.inflate(R.menu.menu1, menu)
            }
            R.id.list1 ->{
                //사용자가 길게 누른 항목 인덱스 번호 파악하기
                val info = menuInfo as AdapterView.AdapterContextMenuInfo
                menu?.setHeaderTitle("리스트 뷰의 메뉴 : ${info.position + 1}")
                menuInflater.inflate(R.menu.menu2, menu)
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        //리스트 항목의 인덱스를 받을 변수
        var position = 0
        when(item.itemId){
            R.id.list_item1, R.id.list_item2 ->{
                //MenuInfo 객체를 추출한다.
                val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
                position = info.position + 1
            }
        }

        //메뉴의 id값으로 분기한다.
        when(item.itemId){
            R.id.text_item1->{
                textView.text = "TextView Menu 1 Clicked"
            }
            R.id.text_item2 ->{
                textView.text = "TextView Menu 2 Clicked"
            }
            R.id.list_item1 ->{
                textView.text = "ListView${position} - 1 Clicked"
            }
            R.id.list_item2 ->{
                textView.text = "ListView${position} - 2 Clicked"
            }
        }
        return super.onContextItemSelected(item)
    }
}