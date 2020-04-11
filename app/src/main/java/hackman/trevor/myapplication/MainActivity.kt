package hackman.trevor.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hackman.trevor.myapplication.search.SearchFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container)

        supportFragmentManager.beginTransaction().add(
            R.id.fragment_container,
            SearchFragment()
        ).commit()
    }
}
