package com.example.myapplication.mainactivity


import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.network.Retrofit.retrofit
import com.example.myapplication.network.api.ApiService
import com.example.myapplication.network.bean.DataModel
import com.example.myapplication.network.bean.SearchReplyBean
import com.example.myapplication.network.bean.Story
import com.example.myapplication.ui.main.BannerAdapter
import com.example.myapplication.ui.main.CustomPagerAdapter
import com.example.myapplication.ui.main.MainViewModel
import com.example.myapplication.ui.main.RVAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar



class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RVAdapter
    private lateinit var bannerAdapter: BannerAdapter
    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        viewModel = MainViewModel()
        // 确保在此处初始化 adapter
        adapter = RVAdapter().apply {
            onItemClick = { position ->
                viewModel.storyList.value?.let {
                    val dataModels = convertStoriesToDataModels(it)
                    val intent = Intent(this@MainActivity, ViewPagerActivity::class.java).apply {
                        putExtra("dataList", ArrayList(dataModels))
                    }
                    startActivity(intent)
                }
            }
        }

        bannerAdapter = BannerAdapter(mutableListOf())

        viewPager2 = findViewById(R.id.rv2)
        recyclerView = findViewById(R.id.rv)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        observeViewModel()
        initRetrofit()
    }

    private fun observeViewModel() {
        viewModel.storyList.observe(this, { stories ->
            stories?.let {
                adapter.submitList(stories)
                viewPager2.adapter = CustomPagerAdapter(convertStoriesToDataModels(stories))
                (viewPager2.adapter as CustomPagerAdapter).notifyDataSetChanged()
            }
        })

        viewModel.topStoryList.observe(this, { topStories ->
            topStories?.let {
                val topStoryImages = topStories.map { it.image }
                bannerAdapter.updateImages(topStoryImages)
            }
        })
    }

    private fun initRetrofit() {
        val BASE_URL = "https://news-at.zhihu.com"
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService.ApiService::class.java)
        fetchData(apiService)
    }

    private fun fetchData(apiService: ApiService.ApiService) {
        val observable: Observable<SearchReplyBean> = apiService.getZhihuData(4)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { zhihuData ->
                    if (zhihuData != null) {
                        viewModel.updateStoryList(zhihuData.stories, zhihuData.top_stories)
                    }
                },
                { error ->
                    Log.e("MainActivity", "网络请求异常: ${error.message}")
                }
            )
    }

    private fun convertStoriesToDataModels(stories: List<Story>): List<DataModel> {
        return stories.map { story ->
            DataModel(
                images = story.images,
                title = story.title,
                hint = story.hint,
                url = story.url
            )
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu) // 加载菜单文件
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_calendar -> {
                showDatePickerDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDayOfMonth"
                Toast.makeText(this, "选择日期: $selectedDate", Toast.LENGTH_SHORT).show()
                val apiService = retrofit.create(ApiService.ApiService::class.java)
                fetchData(apiService)
            },
            year, month, dayOfMonth
        )

        datePickerDialog.show()
    }
}


