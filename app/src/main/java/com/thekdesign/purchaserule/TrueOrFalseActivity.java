package com.thekdesign.purchaserule;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
    int answer_col = 2; // 答案的欄
    int number_of_answers = 1;// 已答題數
    int number_of_questions = 10;// 設定要答題數

    // 是非題部分的宣告
    ImageView btn_selection_true, btn_selection_false;
    TextView tv_theme_TF, tv_questions_TF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.true_false_layout);
        FindViewById();

        OnClickForQuestions();

    }

    private void OnClickForQuestions() {

        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("purchaserule.xls");
            final Workbook workbook = Workbook.getWorkbook(inputStream);
            final Sheet sheet_TF_01 = workbook.getSheet(1);

            int row = sheet_TF_01.getRows();
            final int randomquestion = (int) (Math.random() * row + whichquestion);

            String theme = sheet_TF_01.getName();
            String question = sheet_TF_01.getCell(question_col, randomquestion).getContents();

            MainDisplay(theme, question);

            btn_selection_true.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ChangeRandomQuestion_TF_Right(sheet_TF_01);
                }
            });

            btn_selection_false.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ChangeRandomQuestion_TF_Wrong(sheet_TF_01);
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

    private void ChangeRandomQuestion_TF_Right(Sheet sheet) {
        try {
            String theme;
            String question;
            String answer;
            if (number_of_answers >= number_of_questions) {
                theme = " ";
                question = " ";
                Intent i = new Intent(TrueOrFalseActivity.this, CompleteActivity.class);
                startActivity(i);
            } else {
                int row = sheet.getRows();
                int randomquestion = (int) (Math.random() * row + whichquestion);

                theme = sheet.getName();
                question = sheet.getCell(question_col, randomquestion).getContents().trim();
                answer = sheet.getCell(answer_col, randomquestion).getContents().trim();

                MainDisplay(theme, question);

                if (answer.equals("T")) {
                    Toast.makeText(this, "正解！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "答錯囉！", Toast.LENGTH_SHORT).show();
                }

                number_of_answers++;
            }

        } catch (Exception e) {

        }


    }

    private void ChangeRandomQuestion_TF_Wrong(Sheet sheet) {
        try {
            String theme;
            String question;
            String answer;
            if (number_of_answers >= number_of_questions) {
                theme = " ";
                question = " ";
                Intent i = new Intent(TrueOrFalseActivity.this, CompleteActivity.class);
                startActivity(i);
            } else {
                int row = sheet.getRows();
                int randomquestion = (int) (Math.random() * row + whichquestion);

                theme = sheet.getName();
                question = sheet.getCell(question_col, randomquestion).getContents().trim();
                answer = sheet.getCell(answer_col, randomquestion).getContents().trim();

                MainDisplay(theme, question);

                if (answer.equals("F")) {
                    Toast.makeText(this, "正解！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "答錯囉！", Toast.LENGTH_SHORT).show();
                }

                number_of_answers++;
            }

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
    }
}
