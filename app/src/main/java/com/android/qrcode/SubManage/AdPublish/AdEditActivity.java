package com.android.qrcode.SubManage.AdPublish;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.android.adapter.HorizontalListViewAdapter;
import com.android.base.BaseAppCompatActivity;
import com.android.constant.Constants;
import com.android.qrcode.R;
import com.android.utils.HttpPostUtil;
import com.android.utils.ImageOpera;
import com.android.utils.SelectPicPopupWindow;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;

/**
 * Created by liujunqin on 2016/7/23.
 */
public class AdEditActivity extends BaseAppCompatActivity implements View.OnClickListener {


    @Bind(R.id.toolbar)
    Toolbar toolBar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.add_img)
    ImageView add_img;

    @Bind(R.id.edittsukkomi_grid)
    ListView adEditListView;

    HorizontalListViewAdapter adEditAdapter;
    Bitmap image_add;
    ArrayList<Bitmap> alb;
    private List<String> locationDrr = new ArrayList<String>(); // 文件存放的路径
    private static final int PHOTO_WITH_DATA = 18;  //从SD卡中得到图片
    private static final int PHOTO_WITH_CAMERA = 37;// 拍摄照片
    private static final int SCALE = 2;//照片缩小比例

    private SelectPicPopupWindow menuWindow;// 更新头像弹出框


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_ad_edit);

    }

    @Override
    public void initView() {
        toolBar.setTitle("");
        toolbar_title.setText(R.string.ad_edit_str);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.mipmap.back);
        add_img.setImageResource(R.mipmap.submit);
        add_img.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {

        menuWindow = new SelectPicPopupWindow(this, itemsOnClick);
        image_add = BitmapFactory.decodeResource(getResources(), R.mipmap.ad_icon);
        alb = new ArrayList<>();
        alb.add(image_add);
        adEditAdapter = new HorizontalListViewAdapter(this, alb);
        adEditListView.setAdapter(adEditAdapter);

    }

    @Override
    public void setListener() {

        add_img.setOnClickListener(this);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });


        adEditListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if (alb.size() < 6) {
                    if (position == alb.size() - 1) {
                        //弹出选择框
                        showPopWindow(findViewById(R.id.edittsukkomi_grid_item_image), itemsOnClick);
                    } else {
                        //点击放大图片
                        /*Intent intent  = new Intent();
						intent.setClass(Add_ThingsActivity.this,ShowImgActivity.class);
						intent.putExtra("url",locationDrr.get(position));
						intent.putExtra("position",position+"");
						startActivityForResult(intent,PICREQUEST);*/
                    }
                }
            }
        });

        // 删除图片事件监听
        adEditListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                if (position != alb.size() - 1) {
                    alb.remove(position);
                    adEditAdapter.notifyDataSetChanged();
                    locationDrr.remove(position);
                }

                return true;
            }

        });


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            //case R.id.add_img:


            //    break;

            default:
                break;

        }
    }


    /**
     * @param view
     * @param itemsOnClick
     */
    private void showPopWindow(View view, View.OnClickListener itemsOnClick) {

        menuWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {

            menuWindow.dismiss();
            switch (v.getId()) {

                //拍照按钮
               /* case R.id.btn_take_photo:

                    getImageFromCamera();

                    break;*/

                //相册选择按钮
                case R.id.btn_pick_photo:

                    getImageFromAlbum();

                    break;
                default:

                    break;

            }

        }

    };


    /**
     * 相册选择
     */
    private void getImageFromAlbum() {

        Intent intent = new Intent();
        // 开启Pictures画面Type设定为image
        intent.setType("image/*");
        //使用Intent.ACTION_GET_CONTENT这个Action
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //取得相片后返回到本画面
        startActivityForResult(intent, PHOTO_WITH_DATA);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {


            //从相册里选取
            case PHOTO_WITH_DATA:

                ContentResolver resolver = this.getContentResolver();

                //避免还没选择照片 就点击系统返回 出现崩溃
                if (data == null) {

                    return;
                }

                //照片的原始资源地址
                Uri originalUri = data.getData();
                //使用ContentProvider通过URI获取原始图片
                Bitmap photo;

                try {

                    photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                    if (photo != null) {

                        //这个是为了处理图片显示为黑色的现象，是由于系统超过4.0 ，系统开启的GPU硬件加速导致的  但是系统如果是5.0以上 系统排出在外 不然会出现有黑色背景
                        //解决办法 关闭GPU硬件
                        /*if (SystemUtils.getSystemVersion() >= SystemUtils.V4_0 && SystemUtils.getSystemVersion() < SystemUtils.V5_0) {

                            fragment_user_photo.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                        }*/

                        //由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
                        Bitmap smallBitmap = ImageOpera.zoomBitmap(photo, photo.getWidth() / SCALE, photo.getHeight() / SCALE);
                        photo.recycle();
                        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
                        String time = sDateFormat.format(new java.util.Date());
                        //将压缩过的图片保存到本地文件下 以供上传到服务器 ( 只要把bitmap写入到文件来就行)
                        final String localUrl = ImageOpera.savePicToSdcard(smallBitmap, getOutputMediaFile(), "IMAGE_" + time + ".jpg");
                        locationDrr.add(localUrl);
                        Bitmap showBitmap = getLoacalBitmap(locationDrr.get(locationDrr.size() - 1));
                        alb.add(alb.size() - 1, showBitmap);
                        adEditAdapter.notifyDataSetChanged();
                        smallBitmap.recycle();

                        //开始调接口 如果是批量上传从locationDrr获取路径 如果是单个上传则用下面的方法
                        new  Thread(new Runnable() {
                            @Override
                            public void run() {
                               // UploadUtil.uploadFile(Constants.HOST + Constants.AddAd,localUrl);
                                try {
                                    HttpPostUtil  httpPostUtil = new HttpPostUtil(Constants.HOST + Constants.AddAd,AdEditActivity.this);


                                    httpPostUtil.addFileParameter("file", new File(localUrl));
                                    httpPostUtil.addTextParameter("title", "hello");
                                    byte[] b =  httpPostUtil.send();
                                    String result = new String(b);

                                    Message message = new Message();
                                    message.what = 1;
                                    message.obj = result;
                                    mhandler.sendMessage(message);



                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }
                        }).start();

                    }


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            default:
                break;
        }


    }

    /**
     * 将图片保存到本地文件中
     */
    private String getOutputMediaFile() {

        //get the mobile Pictures directory   /storage/emulated/0/Pictures/IMAGE_20160315_134742.jpg
        File picDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String str = picDir.getPath() + File.separator;
        return str;
    }

    /**
     * 将本地图片转化为bitmap
     *
     * @param url
     * @return
     */
    public Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            File mFile = new File(url);
            return BitmapFactory.decodeStream(fis); // /把流转化为Bitmap图片


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

  private Handler mhandler = new Handler() {

      @Override
      public void handleMessage(Message msg) {
          super.handleMessage(msg);

          switch (msg.what){

              case 1:

                 String result = (String)msg.obj;

                  JSONObject object = null;
                  try {
                      object = new JSONObject(result);
                      if(object.getBoolean("success")){

                          showToast("广告上传成功");
                      }else{

                          showToast("广告上传失败");
                      }

                  } catch (JSONException e) {
                      e.printStackTrace();
                  }


                  break;
              default:

                  break;

          }
      }
  };


}
