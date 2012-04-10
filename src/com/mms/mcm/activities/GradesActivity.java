package com.mms.mcm.activities;

import java.util.List;

import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.mms.mcm.R;
import com.mms.mcm.custom.Constants;
import com.mms.mcm.custom.Utils;
import com.mms.mcm.model.AuthenticateResponse;
import com.mms.mcm.model.Course;
import com.mms.mcm.model.GradesResponse;
import com.mms.mcm.model.Semester;
import com.mms.mcm.network.NetworkCallback;
import com.mms.mcm.network.Parser;
import com.mms.mcm.network.SoapServiceManager;

public class GradesActivity extends Activity implements OnClickListener {

	protected static final String TAG = "Grades Activity";

	private ViewFlipper flipper;
	private Button prevButton;
	private Button nextButton;

	private MihirApp app;

	private AuthenticateResponse authenticateResponse;
	protected GradesResponse gradesResponse;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_grades);
		app = (MihirApp) getApplication();
		authenticateResponse = app.getCurUserInfo();
		initializeViews();
		SoapServiceManager manager = SoapServiceManager
				.getInstance(GradesActivity.this);
		showDialog(Constants.PROGRESSDIALOG);
		manager.sendGetGradesRequest(authenticateResponse.getStudent_ID(),
				callback);
	}

	final NetworkCallback<Object> callback = new NetworkCallback<Object>() {

		public void onSuccess(Object responseObj) {
			removeDialog(Constants.PROGRESSDIALOG);
			// try {

			SoapObject responceObject = (SoapObject) responseObj;
			gradesResponse = Parser.parseGradesResponse(responceObject);
			if(gradesResponse.getErrorMsg().equals(""))
			{
				processResposne(gradesResponse);
			}
			else
			{
				Utils.showDialog(gradesResponse.getErrorMsg(), GradesActivity.this, true);
			}
			/*
			 * } catch (ClassCastException cce) {
			 * Utils.showToast("Unable to process your request",
			 * GradesActivity.this); Log.v(TAG, cce.getMessage());
			 * 
			 * }
			 */
		}

		public void onFailure(String errorMessge) {
			removeDialog(Constants.PROGRESSDIALOG);
			Utils.showToast("Unable to process your request",
					GradesActivity.this);
		}
	};

	private List<Semester> list;

	private TextView examName;

	private void initializeViews() {
		flipper = (ViewFlipper) findViewById(R.id.viewflipper);
		examName=(TextView)findViewById(R.id.marks_tv_examName);
		prevButton = (Button) findViewById(R.id.marks_btn_prevButton);
		prevButton.setOnClickListener(GradesActivity.this);
		nextButton = (Button) findViewById(R.id.marks_btn_nextbutton);
		nextButton.setOnClickListener(GradesActivity.this);
	}

	private void processResposne(GradesResponse gradesResponse) {
		list = gradesResponse.getSemesters();

		for (int i = (list.size() - 1); i >= 0; i--) {
			Semester semester=list.get(i);
			if(semester!=null)
			{
				flipper.addView(getView(semester));
			}
			setSemesterName();
		}
	}

	private View getView(Semester semester) {
		TableLayout layout;
		View v = LayoutInflater.from(GradesActivity.this).inflate(
				R.layout.sample, null);
		layout=(TableLayout) v.findViewById(R.id.marks_row_parent);
		List<Course> courseList = semester.getCourseList();
		if (courseList != null) {
			
			for (int i = 0; i < courseList.size(); i++) {

				View view = LayoutInflater.from(GradesActivity.this).inflate(
						R.layout.layout_grades_data, null);
				TableRow row = (TableRow) view.findViewById(R.id.marks_row);
				((TextView) row.findViewById(R.id.course_code))
						.setText(courseList.get(i).getCourseCode());
				((TextView) row.findViewById(R.id.course_name))
						.setText(courseList.get(i).getCourseName());
				((TextView) row.findViewById(R.id.course_grade))
						.setText(courseList.get(i).getCourseGrade());
				layout.addView(row);
			}
		}

		return layout;
	}

	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case Constants.PROGRESSDIALOG:
			final ProgressDialog progressDialog = new ProgressDialog(
					GradesActivity.this);
			progressDialog.setMessage("Please wait....");
			progressDialog.show();
			progressDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					if (!progressDialog.isShowing()) {
						progressDialog.show();
					}
				}
			});
			return progressDialog;
		default:
			break;
		}
		return super.onCreateDialog(id);
	}

	private void setSemesterName() {
		if (list != null) {
			int index = flipper.getChildCount()
					- (flipper.getDisplayedChild() + 1);
			Semester term = list.get(index);
			examName.setText(term.getSemesterName());
		}
	}
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.marks_btn_prevButton:
			// nextButton.setVisibility(View.VISIBLE);
			nextButton.setEnabled(true);
			int count = flipper.getChildCount();
			int index = flipper.getDisplayedChild();

			if (index + 2 == count) {
				prevButton.setEnabled(false);
				nextButton.setEnabled(true);
			}

			flipper.showNext();
			setSemesterName();
			break;

		case R.id.marks_btn_nextbutton:
			prevButton.setEnabled(true);
			int count1 = flipper.getChildCount();
			int index1 = flipper.getDisplayedChild();
			if (index1 + (count1 - 1) == count1) {
				nextButton.setEnabled(false);
				prevButton.setEnabled(true);
			}
			flipper.showPrevious();
			setSemesterName();
			break;

		default:
			break;

		}
	}

}
