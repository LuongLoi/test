package com.example.appshopping.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;
import android.widget.ViewFlipper;
import android.widget.ImageView;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.appshopping.R;
import com.example.appshopping.adapter.LoaiSPAdapter;
import com.example.appshopping.adapter.SanphamAdapter;
import com.example.appshopping.model.GioHang;
import com.example.appshopping.model.Loaisp;
import com.example.appshopping.model.Sanpham;
import com.example.appshopping.ultil.CheckConnection;
import com.example.appshopping.ultil.Server;
import com.google.android.material.navigation.NavigationView;
//import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolBar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    ListView listView;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ArrayList<Loaisp> mangloaisp;
    LoaiSPAdapter loaiSPAdapter;
    int idLoaiSP = 0;
    String tenLoaiSP = "";
    String hinhLoaiSP = "";
    ArrayList<Sanpham> mangsp;
    SanphamAdapter sanphamAdapter;
    public static ArrayList<GioHang> manggiohang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            ActionBar();
            ActionViewFlipper();
            CatchOnItemListView();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(),"Ki???m tra k???t n???i Internet");
            finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void CatchOnItemListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Ki???m tra k???t n???i Internet");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this,DienThoaiActivity.class);
                            intent.putExtra("idLoaiSP",mangloaisp.get(i).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Ki???m tra k???t n???i Internet");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void ActionViewFlipper() {
        ArrayList<String> hinhquangcao = new ArrayList<>();
        hinhquangcao.add("https://cdn.tgdd.vn/Products/Images/42/285696/Slider/samsung-galaxy-z-flip4-5g-512gb637967648218460074.jpg");
        hinhquangcao.add("https://cdn.tgdd.vn/Products/Images/42/285696/Slider/samsung-galaxy-z-flip4-5g-512gb637967648222940105.jpg");
        hinhquangcao.add("https://cdn.tgdd.vn/Products/Images/42/285696/Slider/samsung-galaxy-z-flip4-5g-512gb637967648225800086.jpg");
        hinhquangcao.add("https://cdn.tgdd.vn/Products/Images/42/285696/Slider/samsung-galaxy-z-flip4-5g-512gb637967648233530149.jpg");
        hinhquangcao.add("https://cdn.tgdd.vn/Products/Images/42/285696/Slider/samsung-galaxy-z-flip4-5g-512gb637967648236830151.jpg");
        hinhquangcao.add("https://cdn.tgdd.vn/Products/Images/42/285696/Slider/samsung-galaxy-z-flip4-5g-512gb637967648230020131.jpg");
        hinhquangcao.add("https://cdn.tgdd.vn/Products/Images/42/285696/Slider/samsung-galaxy-z-flip4-5g-512gb637967648239970269.jpg");
        hinhquangcao.add("https://cdn.tgdd.vn/Products/Images/42/285696/Slider/samsung-galaxy-z-flip4-sld-1020x570.jpg");
        for (int i = 0; i < hinhquangcao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(hinhquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);

    }

    private void ActionBar() {
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Anhxa() {
        toolBar = findViewById(R.id.toolbar);
        viewFlipper = findViewById(R.id.viewFlipper);
        recyclerView = findViewById(R.id.recyclerview);
        listView = findViewById(R.id.listview);
        navigationView = findViewById(R.id.navigation);
        drawerLayout = findViewById(R.id.drawerlayout);
        mangloaisp = new ArrayList<>();
        mangloaisp.add(0,new Loaisp(0,"Trang ch??nh","https://cdn-icons-png.flaticon.com/512/25/25694.png"));
        mangloaisp.add(1,new Loaisp(1, "??i???n tho???i", "https://cdn.tgdd.vn/Products/Images/42/258047/samsung-galaxy-z-flip4-5g-128gb-thumb-tim-600x600.jpg"));
        loaiSPAdapter = new LoaiSPAdapter(mangloaisp,getApplicationContext());
        listView.setAdapter(loaiSPAdapter);
        mangsp = new ArrayList<>();
        mangsp.add(new Sanpham(1,"Samsung Galaxy Z Flip4 5G",1,"https://cdn.tgdd.vn/Products/Images/42/258047/samsung-galaxy-z-flip4-5g-128gb-thumb-tim-600x600.jpg","Samsung Galaxy Z Flip4 128GB ???? ch??nh th???c ra m???t th??? tr?????ng c??ng ngh???, ????nh d???u s??? tr??? l???i c???a Samsung tr??n con ???????ng ?????nh h?????ng ng?????i d??ng v??? s??? ti???n l???i tr??n nh???ng chi???c ??i???n tho???i g???p. V???i ????? b???n ???????c gia t??ng c??ng ki???u thi???t k??? ?????p m???t gi??p Flip4 tr??? th??nh m???t trong nh???ng t??m ??i???m s??ng gi?? cho n???a cu???i n??m 2022.",1));
        mangsp.add(new Sanpham(2, "Xiaomi Redmi Note 11",1, "https://cdn.tgdd.vn/Products/Images/42/269831/Xiaomi-redmi-note-11-black-200x200.jpeg","??i???n tho???i Redmi ???????c m???nh danh l?? d??ng s???n ph???m qu???c d??n ngon - b???  - r??? c???a Xiaomi v?? Redmi Note 11 (4GB/64GB) c??ng kh??ng ph???i ngo???i l???, m??y s??? h???u m???t hi???u n??ng ???n ?????nh, m??n h??nh 90 Hz m?????t m??, c???m camera AI ?????n 50 MP c??ng m???t m???c gi?? v?? c??ng t???t.",1));
        mangsp.add(new Sanpham(3, "Vivo Y15s",1,"https://cdn.tgdd.vn/Products/Images/42/249720/vivo-y15s-2021-261021-114837-200x200.jpg","Vivo v???a mang m???t chi???n binh m???i ?????n ph??n kh??c t???m trung gi?? r??? c?? t??n Vivo Y15s, m???t s???n ph???m s??? h???u kh?? nhi???u ??u ??i???m nh?? thi???t k??? ?????p, m??n h??nh l???n, camera k??p, pin c???c tr??u v?? c??n r???t nhi???u ??i???u th?? v??? kh??c ??ang ch??? ????n b???n. Vivo Y15s s??? h???u nhi???u ??i???m t????ng ?????ng v???i nh???ng \"ng?????i anh em\" Vivo Y15 c???a m??nh khi to??n b??? th??n m??y l??m b???ng Polymer cao c???p, thi???t k??? cong c???nh 3D v?? ki???u d??ng m???ng nh??? ch??? 8.28 mm ??em l???i c???m gi??c c???m m??y trong tay kh?? tho???i m??i. M???t l??ng ho??n thi???n v???i h???a ti???t k??? s???c m??? v???i hai t??y ch???n m??u s???c Xanh Bi???n S??u v?? Tr???ng c?? kh??? n??ng chuy???n s??ng xanh v?? c??ng ?????p m???t.",1));
        sanphamAdapter = new SanphamAdapter(getApplicationContext(),mangsp);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(sanphamAdapter);
        if (manggiohang == null) {
            manggiohang = new ArrayList<>();
        }
    }
}