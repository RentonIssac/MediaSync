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
	 * ��ʼ������չ�б�
	 */
	private void initExpandListView() {
		statusAdapter = new StatusExpandAdapter(context, getListData());
		expandlistView.setAdapter(statusAdapter);
		expandlistView.setGroupIndicator(null); // ȥ��Ĭ�ϴ��ļ�ͷ
		// expandlistView.setSelection(0);// ����Ĭ��ѡ����

		// ��������group
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
					if (groupPosition != i && count > 2) {// �ر���������
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
						"������һ���ƣ��ٰ�Ҳ����ͷ",
						"��һ�����ϣ���һ�˰��ס�",
						"��ʱ����Ů�����������ܾ����ԭ��ֻ��������һ��������ϴͷ������������벻ֵ����ϴͷ��Ů����ҪԼ�˳���Ҳ������ԭ��һ����ϴ��ͷ�������治���ģ�����ͻȻ�����ĳ�ҵĶ�����",
						"�Ҽ���ǧ��������ķ��������ȴ�������������" },
				{
						"��˵���಻��Ҫ������Ϊ�㳤��һ�ž����˯��Ҳ�����ĵ�������˵�ɼ�����Ҫ������Ϊ���������ֲ�С�Ŀ��˴��꼶ǰ�塣��˵��������Ҫ������Ϊ����߱�̥��Ŀ��԰��������齫�ˡ���˵�Ҿ�����Ҫ������Ϊ����һ��������һ��ü�͸������¿�ĸ�ĸ����˵��������Ҫ������Ϊ�㲻���ҹ��Ϊ��ʹ��������ȥ�ȵ�˺���ѷΡ���˵����Ҫ��������Ϊ���Ѿ�ӵ���ˣ���˵����Ҫ��������Ϊ�������֪�����˵�Ŭ����������",
						"����Զ��֪�����㷢�˸����š����ߡ�Ŷ�����ܼ����ظ�����ˣ����ж��ں��㣡",
						"����˵�Ļ����۾���ݸ������������" },
				{ "��Щ���˺þò������׵��£����ǻᱻż��������ʧ��ȫ���Ʒ���",
						"��ĥ�˵Ĳ�����𣬶��Ǹж��Ļ��䣬���˺�����վ��ԭ�ػ���Ϊ�ص�ȥ", "������һ���ƣ��ٰ�Ҳ����ͷ��",
						"����һ����ˮ�����ң�Ҳ����һ����ˮ�����ң�������һ����ˮ�����ң���Ҫ���Ǻڰ׷���ֱ������" } };
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
		// �����ݰ���ʱ������
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
