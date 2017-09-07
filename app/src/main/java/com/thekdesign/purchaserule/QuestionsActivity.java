package com.thekdesign.purchaserule;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;

public class QuestionsActivity extends AppCompatActivity {

    // 選擇題部分的宣告
    Button btn_selection_1, btn_selection_2, btn_selection_3, btn_selection_4;
    TextView tv_theme, tv_questions;
    //設定讀取行列
    int whichquestion = 2; // 問題的列
    int question_col = 3; // 問題的欄
    int selection_col = 4; // 選項的欄
    int answer_col = 2; // 答案的欄
    int number_of_answers = 1;// 已答題數
    int number_of_questions = 10;// 設定要答題數
    String answer_user_select = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions_layout);
        FindViewById();
        FirstView();
        SelectionOnClickListners();
    }

    private void OnClickForQuestions() {

        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("purchaserule.xls");
            Workbook workbook = Workbook.getWorkbook(inputStream);
            final Sheet sheet_Choice_01 = workbook.getSheet(0);

            int row = sheet_Choice_01.getRows();
            int randomquestion = (int) (Math.random() * row + whichquestion);
            String answer = sheet_Choice_01.getCell(answer_col, randomquestion).getContents();

            String theme = sheet_Choice_01.getName();
            String question = sheet_Choice_01.getCell(question_col, randomquestion).getContents();

            String selection_1 = sheet_Choice_01.getCell(selection_col, randomquestion).getContents();
            String selection_2 = sheet_Choice_01.getCell(selection_col + 1, randomquestion).getContents();
            String selection_3 = sheet_Choice_01.getCell(selection_col + 2, randomquestion).getContents();
            String selection_4 = sheet_Choice_01.getCell(selection_col + 3, randomquestion).getContents();

            if (number_of_answers >= number_of_questions) {
                Intent i = new Intent(this, CompleteActivity.class);
                startActivity(i);
            } else {
                MainDisplay(theme, question);
                SelectionsDisplay(selection_1, selection_2, selection_3, selection_4);
            }
            if (answer_user_select.equals(answer) && !answer_user_select.equals("")) {
                Toast.makeText(this, "正解！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "答錯囉！", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {

        }

    }

    private void FirstView() {
        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("purchaserule.xls");
            Workbook workbook = Workbook.getWorkbook(inputStream);
            final Sheet sheet_Choice_01 = workbook.getSheet(0);

            int row = sheet_Choice_01.getRows();
            int randomquestion = (int) (Math.random() * row + whichquestion);

            String theme = sheet_Choice_01.getName();
            String question = sheet_Choice_01.getCell(question_col, randomquestion).getContents();

            String selection_1 = sheet_Choice_01.getCell(selection_col, randomquestion).getContents();
            String selection_2 = sheet_Choice_01.getCell(selection_col + 1, randomquestion).getContents();
            String selection_3 = sheet_Choice_01.getCell(selection_col + 2, randomquestion).getContents();
            String selection_4 = sheet_Choice_01.getCell(selection_col + 3, randomquestion).getContents();

            MainDisplay(theme, question);
            SelectionsDisplay(selection_1, selection_2, selection_3, selection_4);

        } catch (Exception e) {

        }
    }

    private void ChangeAnotherQuestion(Sheet sheet) {
        try {

            int row = sheet.getRows();
            int col = sheet.getColumns();

            String theme = sheet.getName();
            String question = sheet.getCell(question_col, whichquestion).getContents();
            String selection_1 = sheet.getCell(selection_col, whichquestion).getContents();
            String selection_2 = sheet.getCell(selection_col + 1, whichquestion).getContents();
            String selection_3 = sheet.getCell(selection_col + 2, whichquestion).getContents();
            String selection_4 = sheet.getCell(selection_col + 3, whichquestion).getContents();

            if (whichquestion <= row) {
                whichquestion++;
            } else {
                whichquestion = 2;
            }


            MainDisplay(theme, question);
            SelectionsDisplay(selection_1, selection_2, selection_3, selection_4);

        } catch (Exception e) {

        }


    }

    private void ChangeRandomQuestion(Sheet sheet) {
        try {

            int row = sheet.getRows();
            int randomquestion = (int) (Math.random() * row + whichquestion);
            String answer = sheet.getCell(answer_col, randomquestion).getContents();

            String theme = sheet.getName();
            String question = sheet.getCell(question_col, randomquestion).getContents();

            String selection_1 = sheet.getCell(selection_col, randomquestion).getContents();
            String selection_2 = sheet.getCell(selection_col + 1, randomquestion).getContents();
            String selection_3 = sheet.getCell(selection_col + 2, randomquestion).getContents();
            String selection_4 = sheet.getCell(selection_col + 3, randomquestion).getContents();

            MainDisplay(theme, question);
            SelectionsDisplay(selection_1, selection_2, selection_3, selection_4);

            SelectionOnClickListners();
        } catch (Exception e) {

        }


    }

    private void GetQuestionsFormExcel(Workbook workbook, Sheet sheet) {
        try {

            int num_sheets = workbook.getNumberOfSheets();
            int row = sheet.getRows();
            int col = sheet.getColumns();
            String theme = sheet.getName();
            String question = sheet.getCell(3, 3).getContents();
            String selection_1 = sheet.getCell(4, 3).getContents();
            String selection_2 = sheet.getCell(5, 3).getContents();
            String selection_3 = sheet.getCell(6, 3).getContents();
            String selection_4 = sheet.getCell(7, 3).getContents();

            Toast.makeText(QuestionsActivity.this, "all rows = " + row + "\n"
                    + "all cols = " + col + "\n"
                    + "all sheets = " + num_sheets, Toast.LENGTH_SHORT).show();
            MainDisplay(theme, question);
            SelectionsDisplay(selection_1, selection_2, selection_3, selection_4);

//            display(text_form_excel);
        } catch (Exception e) {

        }


    }

    private void MainDisplay(String theme, String question) {
        tv_theme.setText(theme);
        tv_questions.setText(question);
    }

    private void SelectionsDisplay(String selection_1, String selection_2, String selection_3, String selection_4) {
        btn_selection_1.setText(selection_1);
        btn_selection_2.setText(selection_2);
        btn_selection_3.setText(selection_3);
        btn_selection_4.setText(selection_4);
    }

    private void SelectionOnClickListners() {

        btn_selection_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer_user_select = "1";
                OnClickForQuestions();
                number_of_answers++;
            }
        });
        btn_selection_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer_user_select = "2";
                OnClickForQuestions();
                number_of_answers++;
            }
        });
        btn_selection_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer_user_select = "3";
                OnClickForQuestions();
                number_of_answers++;
            }
        });
        btn_selection_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer_user_select = "4";
                OnClickForQuestions();
                number_of_answers++;
            }
        });

    }

    private void FindViewById() {
        tv_theme = (TextView) findViewById(R.id.tv_theme);
        tv_questions = (TextView) findViewById(R.id.tv_questions);
        btn_selection_1 = (Button) findViewById(R.id.btn_selection_1);
        btn_selection_2 = (Button) findViewById(R.id.btn_selection_2);
        btn_selection_3 = (Button) findViewById(R.id.btn_selection_3);
        btn_selection_4 = (Button) findViewById(R.id.btn_selection_4);
    }
}
