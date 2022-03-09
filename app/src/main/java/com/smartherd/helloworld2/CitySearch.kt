package com.smartherd.helloworld2

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
/*import android.widget.SearchView*/
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_recyclerview.*
import java.util.*
import kotlin.collections.ArrayList

class CitySearch : AppCompatActivity() {

    val arrayList = ArrayList<CityModel>()
    val displayList = ArrayList<CityModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)
/*
        val actionBar : ActionBar? = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setDisplayShowHomeEnabled(true)
*/
        arrayList.add(CityModel("Split", "Croatia", "The largest city on the Adriatic Coast, seaside delight Split has long history. Sites such as Diocletian's Palace, the Cathedral and Bell Tower can prove it. There is an abundance of restaurants and wine cellars, people are warm-welcome and the views are breathtaking. Beaches are full of life but still relaxing. A place you wouldn't want to leave soon.", R.drawable.split_circle))
        arrayList.add(CityModel("Amsterdam", "Netherlands", "This city, full of colorful homes, canals and bridges, is one of Europe's most picturesque capitals. Must-sees on any visitor's itinerary include the Anne Frank House, the Van Gogh Museum and the world's only floating flower market.", R.drawable.amst_circle))
        arrayList.add(CityModel("Berlin", "Germany", "Berlin is an edgy city, from its fashion to its architecture to its charged political history. The Berlin Wall is a sobering reminder of the hyper-charged postwar atmosphere, and yet the graffiti art that now covers its remnants has become symbolic of social progress.", R.drawable.berlin_circle))
        arrayList.add(CityModel("Lisbon", "Portugal", "Nestled among seven hills, this immensely walkable city offers an enchanting alternative to Europe’s more popular capitals. With a lively nightlife scene, festive markets, and vibrant museums, Lisbon provides plenty of options to burn off a few mandatory glasses of Porto, a generous serving of bacalhau, and days’ worth of pastéis de nata.", R.drawable.lisbon_circle))
        arrayList.add(CityModel("New York City", "USA", "Luxe hotels. Gritty dive bars. Broadway magic. Side-street snack carts. Whether you’re a first-time traveler or a long-time resident, NYC is a city that loves to surprise. The unrivaled mix of iconic arts spaces, endless shopping experiences, architectural marvels, and proudly distinct neighborhoods.", R.drawable.newy_circle))
        arrayList.add(CityModel("Rome", "Italy", "The sprawling city of Rome remains one of the most significant stops in the world, thanks to its seamless blend of Old World wonders and modern delights. The ruins of the Colosseum, her iconic fountains, lazy wanders through cobblestone streets with gelato in hand: All this and more beckon. ",R.drawable.rome_circle))
        arrayList.add(CityModel("Barcelona", "Spain", "Bustling markets, tree lined blocks, and fantastical architecture cozy up to one another in this dreamy Mediterranean beach town. Paella and pintxos bars, exceptional seafood, standout local wines, a world-class arts scene, and bumping nightlife.", R.drawable.barc_circle))
        arrayList.add(CityModel("London", "England", "London is layered with history, where medieval and Victorian complement a rich and vibrant modern world. The Tower of London and Westminster neighbor local pubs and markets, and time-worn rituals like the changing of the guards take place as commuters rush to catch the Tube.", R.drawable.london_circle))
        arrayList.add(CityModel("Dublin", "Ireland", "You've probably heard that Guinness tastes better in Dublin (fresh from the factory), but what you may not know is that Dublin is a perfect destination for the whole family. No, we're not suggesting you let the kiddies drink a pint. Instead, take them to the Dublin Zoo.", R.drawable.dublin_circle))
        arrayList.add(CityModel("San Marino", "Republic of San Marino", "For such a minuscule country, San Marino provides a fascinating collection of unique facts, outstanding views and an impressive history.  Given its size though, many people opt to take just a day trip to San Marino from one of it’s neighbouring Italian cities and not spend a night or more getting to know it.", R.drawable.sanm_circle))
        displayList.addAll(arrayList)

        val myAdapter = CityAdapter(displayList, this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = myAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu, menu)
        val menuItem = menu!!.findItem((R.id.search))

        if(menuItem != null) {

            val searchView = menuItem.actionView as SearchView

            val editText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
            editText.hint = "Search..."

            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    if(newText!!.isNotEmpty()) {

                        displayList.clear()
                        val search = newText.toLowerCase(Locale.getDefault())
                        arrayList.forEach{

                            if(it.title.toLowerCase(Locale.getDefault()).contains(search)) {
                                displayList.add(it)
                            }
                        }
                        recyclerView.adapter!!.notifyDataSetChanged()
                    }
                    else {
                        displayList.clear()
                        displayList.addAll(arrayList)
                        recyclerView.adapter!!.notifyDataSetChanged()
                    }

                    return true
                }

            })
        }

        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}