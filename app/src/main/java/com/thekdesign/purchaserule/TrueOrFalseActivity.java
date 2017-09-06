package com.thekdesign.purchaserule;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;

public class TrueOrFalseActivity extends AppCompatActivity {

    //設定讀取行列
    int whichquestion = 2; // 問題的列
    int question_col = 3; // 問題的欄

    // 是非題部分的宣告
    ImageView btn_selection_true, btn_selection_false;
    TextView tv_theme_TF, tv_questions_TF;
    Button btn_summit_TF, btn_next_TF, btn_random_TF;

    Button btn_changetoC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.true_false_layout);
        FindViewById();
        OnClickForQuestions();

        btn_changetoC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TrueOrFalseActivity.this, QuestionsActivity.class);
                startActivity(i);
            }
        });
    }

    private void OnClickForQuestions() {

        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("purchaserule.xls");
            final Workbook workbook = Workbook.getWorkbook(inputStream);
            final Sheet sheet_TF_01 = workbook.getSheet(1);

            //是非題的Button OnClick事件宣告
            btn_summit_TF.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GetQuestionsFormExcel_TF(workbook, sheet_TF_01);
                }
            });

            btn_next_TF.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ChangeAnotherQuestion_TF(sheet_TF_01);
                }
            });

            btn_random_TF.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ChangeRandomQuestion_TF(sheet_TF_01);
                }
            });

        } catch (Exception e) {

        }

    }

    private void ChangeAnotherQuestion_TF(Sheet sheet) {
        try {

            int row = sheet.getRows();

            String theme = sheet.getName();
            String question = sheet.getCell(question_col, whichquestion).getContents();

            if (whichquestion <= row) {
                whichquestion++;
            } else {
                whichquestion = 2;
            }


            MainDisplay(theme, question);

        } catch (Exception e) {

        }


    }

    private void ChangeRandomQuestion_TF(Sheet sheet) {
        try {

            int row = sheet.getRows();
            int randomquestion = (int) (Math.random() * row + whichquestion);

            String theme = sheet.getName();
            String question = sheet.getCell(question_col, randomquestion).getContents();

            MainDisplay(theme, question);

        } catch (Exception e) {

        }


    }

    private void GetQuestionsFormExcel_TF(Workbook workbook, Sheet sheet) {
        try {

            int num_sheets = workbook.getNumberOfSheets();
            int row = sheet.getRows();
            int col = sheet.getColumns();
            String theme = sheet.getName();
            String question = sheet.getCell(3, 2).getContents();

            Toast.makeText(TrueOrFalseActivity.this, "all rows = " + row + "\n"
                    + "all cols = " + col + "\n"
                    + "all sheets = " + num_sheets, Toast.LENGTH_SHORT).show();
            MainDisplay(theme, question);

        } catch (Exception e) {

        }


    }

    private void MainDisplay(String theme, String question) {
        tv_theme_TF.setText(theme);
        tv_questions_TF.setText(question);
    }

    private void FindViewById() {

        tv_theme_TF = (TextView) findViewById(R.id.tv_theme_TF);
        tv_questions_TF = (TextView) findViewById(R.id.tv_questions_TF);
        btn_selection_true = (ImageView) findViewById(R.id.btn_selection_true);
        btn_selection_false = (ImageView) findViewById(R.id.btn_selection_false);

        btn_summit_TF = (Button) findViewById(R.id.btn_summit_TF);
        btn_next_TF = (Button) findViewById(R.id.btn_next_TF);
        btn_random_TF = (Button) findViewById(R.id.btn_random_TF);

        btn_changetoC = (Button) findViewById(R.id.btn_changetoC);
    }
}
