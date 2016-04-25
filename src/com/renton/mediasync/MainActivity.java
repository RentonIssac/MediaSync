package com.renton.mediasync;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class MainActivity extends Activity {

	public static final String TAG = "MainActivity";

	private final String IMAGE_TYPE = "image/*";
	private final int IMAGE_CODE = 0;
	
	public static String UPLOAD_FILE_NAME = "";
	
	/******************************************************/
	private ExpandableListView expandlistView;
	private StatusExpandAdapter statusAdapter;
	private Context context;
	private int count = 0;
	/******************************************************/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		context = this;
		expandlistView = (ExpandableListView) findViewById(R.id.expandlist);
		initExpandListView();
	}
	
	/**
	 * 初始化可拓展列表
	 */
	private void initExpandListView() {
		statusAdapter = new StatusExpandAdapter(context, getListData());
		expandlistView.setAdapter(statusAdapter);
		expandlistView.setGroupIndicator(null); // 去掉默认带的箭头
		// expandlistView.setSelection(0);// 设置默认选中项

		// 遍历所有group
		int groupCount = expandlistView.getCount();
		expandlistView.expandGroup(0);
		for (int i = 0; i < groupCount; i++) {
			if (i <= 1) {
				expandlistView.expandGroup(i);
			}

		}

		expandlistView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {

				return false;
			}
		});
		expandlistView.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				count++;
				for (int i = 0, count = expandlistView
						.getExpandableListAdapter().getGroupCount(); i < count; i++) {
					if (groupPosition != i && count > 2) {// 关闭其他分组
						expandlistView.collapseGroup(i);
						count = 1;
					}
				}

			}
		});
	}
	
	private List<GroupStatusEntity> getListData() {
		List<GroupStatusEntity> groupList;
		String[] strArray = new String[] { "20140710", "20081201", "20150809" };
		String[][] childTimeArray = new String[][] {
				{
						"敬往事一杯酒，再爱也不回头",
						"择一城终老，遇一人白首。",
						"有时候邀女生出来玩她拒绝你的原因只有两个，一是她懒得洗头，二是你的邀请不值得她洗头。女生非要约人出来也有两个原因，一是她洗了头不出来玩不甘心，二是突然很想吃某家的东西。",
						"我见过千万人像你的发像你的眼却都不是你的脸。" },
				{
						"你说长相不重要，是因为你长了一张就算刚睡醒也敢自拍的脸。你说成绩不重要，是因为你随随便便又不小心考了次年级前五。你说恋爱不重要，是因为你身边备胎多的可以摆四五桌麻将了。你说家境不重要，是因为你有一个看你皱一下眉就给你买新款的父母。你说健康不重要，是因为你不会半夜因为疼痛而翻来覆去咳得撕心裂肺。你说不重要不过是因为你已经拥有了，你说不重要不过是因为你从来不知道别人的努力和挣扎。",
						"你永远不知道在你发了个“嗯”或者“哦”还能继续回复你的人，是有多在乎你！",
						"最想说的话在眼睛里，草稿箱里，还有梦里" },
				{ "那些花了好久才想明白的事，总是会被偶尔的情绪失控全部推翻。",
						"折磨人的不是离别，而是感动的回忆，让人很容易站在原地还以为回得去", "敬往事一杯酒，再爱也不回头！",
						"可以一杯滚水烫死我，也可以一杯冰水冷死我，但不能一杯温水耗着我，我要的是黑白分明直接利落" } };
		groupList = new ArrayList<GroupStatusEntity>();
		for (int i = 0; i < strArray.length; i++) {
			GroupStatusEntity groupStatusEntity = new GroupStatusEntity();
			groupStatusEntity.setGroupName(strArray[i]);
		
			List<ChildStatusEntity> childList = new ArrayList<ChildStatusEntity>();

			for (int j = 0; j < childTimeArray[i].length; j++) {
				ChildStatusEntity childStatusEntity = new ChildStatusEntity();
				childStatusEntity.setContentText(childTimeArray[i][j]);
				if (j % 3 == 0) {
					childStatusEntity.setImgSrc(R.drawable.one);
				}
				if (j % 3 == 1) {
					childStatusEntity.setImgSrc(R.drawable.two);
				}
				if (j % 3 == 2) {
					childStatusEntity.setImgSrc(R.drawable.three);
				}
				childStatusEntity.setIsfinished(true);
				childList.add(childStatusEntity);
			}

			groupStatusEntity.setChildList(childList);

			groupList.add(groupStatusEntity);
		}
		// 将数据按照时间排序
		DateComparator comparator = new DateComparator();
		Collections.sort(groupList, comparator);
		return groupList;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {

			/*
			 * AsyncHttpClient client = new AsyncHttpClient(); File myFile = new
			 * File("/path/to/file.png"); RequestParams params = new
			 * RequestParams(); try { params.put("profile_picture", myFile); }
			 * catch(FileNotFoundException e) {}
			 */

			Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
			getAlbum.setType(IMAGE_TYPE);
			startActivityForResult(getAlbum, IMAGE_CODE);

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {

			if (requestCode == IMAGE_CODE) {
				
				try {
					Uri selectedImage = data.getData();
					String wholeID = DocumentsContract
							.getDocumentId(selectedImage);

					// Split at colon, use second item in the array
					String id = wholeID.split(":")[1];

					String[] column = { MediaStore.Images.Media.DATA };

					// where id is equal to
					String sel = MediaStore.Images.Media._ID + "=?";

					Cursor cursor = getContentResolver()
							.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
									column, sel, new String[] { id },
									null);

					String filePath = "";

					int columnIndex = cursor.getColumnIndex(column[0]);

					if (cursor.moveToFirst()) {
						filePath = cursor.getString(columnIndex);
					}
					cursor.close();

					Log.e("onActivityResult path", filePath);
					
					UPLOAD_FILE_NAME = filePath;

				} catch (Exception e) {
					Log.e("onActivityResult", e.toString());
				}
				
				/*new Thread(new Runnable() {
					@Override
					public void run() {
						File myFile = new File(UPLOAD_FILE_NAME);
						//FileImageUpload.uploadFile(myFile, "http://168.168.1.26:8000/upload_json/");
						RequestParams params = new RequestParams();
						try {
							params.put("mediafile", myFile);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
						params.put("owner", "renton");
						
						AsyncHttpClient client = new AsyncHttpClient();
						client.post("http://168.168.1.26:8000/upload_json/", params, new AsyncHttpResponseHandler() {

							@Override
							public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
								Log.e("onActivityResult", "onFailure");
							}

							@Override
							public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
								Log.e("onActivityResult", "onSuccess");
							}
							
						});
					}
				}).start();*/
				File myFile = new File(UPLOAD_FILE_NAME);
				//FileImageUpload.uploadFile(myFile, "http://168.168.1.26:8000/upload_json/");
				RequestParams params = new RequestParams();
				try {
					params.put("mediafile", myFile);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				params.put("owner", "renton");
				
				AsyncHttpClient client = new AsyncHttpClient();
				client.post("http://168.168.1.26:8000/upload_json/", params, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONObject response/*JSONArray response*/) {

						//super.onSuccess(statusCode, headers, response);
						//if (statusCode == 200) {
						if (statusCode == 200) {
							/*
							for (int i = 0; i < response.length(); i++) {
								try {
									JSONObject obj = response.getJSONObject(i);
									int rc = obj.getInt("rc");
									String msg = obj.getString("message");

									Log.e("response", "rc: " + rc + ", msg: " + msg);
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}*/
							
							int rc;
							String msg;
							try {
								rc = response.getInt("rc");
								msg = response.getString("message");
								Log.e("response", "rc: " + rc + ", msg: " + msg);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}
				});
			}
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

}
