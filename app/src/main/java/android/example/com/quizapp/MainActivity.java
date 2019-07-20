package android.example.com.quizapp;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    /** score variable is global */
    int score = 0;

    //@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This function makes sure all questions were answered
     * @param checkbox1 holds the specific checkbox being validated q1
     * @param checkbox2 holds the specific checkbox being validated q1
     * @param checkbox3 holds the specific checkbox being validated q1
     * @param radioGroup2 holds the specific radio group being validated q2
     * @param radioGroup3 holds the specific radio group being validated q3
     * @param radioGroup4 holds the specific radio group being validated q4
     * @param textField holds the specific text being validated q5
     * @return turns 1 (valid) or 0 (not valid)
     */

    public int validateForm(CheckBox checkbox1, CheckBox checkbox2, CheckBox checkbox3, RadioGroup radioGroup2, RadioGroup radioGroup3, RadioGroup radioGroup4, String textField) {
        int isValid = 1;

        if (!(checkbox1.isChecked()) && !(checkbox2.isChecked()) && !(checkbox3.isChecked())) {
            isValid = 0;
        }

        if (validateAnswers(radioGroup2) == 0) {
            isValid = 0;
        }
        if (validateAnswers(radioGroup3) == 0) {
            isValid = 0;
        }
        if (validateAnswers(radioGroup4) == 0) {
            isValid = 0;
        }

        /** validate text field */
            if (textField.length() == 0) {
            isValid = 0;
        } else {
            score++;  /** they get a score for entering a value */
        }

        return isValid;
    }

    /** this function checks to make sure an answer has
     * been selected in the radioGroups (q2, q3, q4)
     *
     * @param radioGroup holds the specific radio group being validated
     * @return turns 1 (valid) or 0 (not valid)
     */

    public int validateAnswers(RadioGroup radioGroup) {
        int checkIsValid = 1;

        /**
         * validate that the group being passed
         * has been answered correctly
         * */

        if (radioGroup.getCheckedRadioButtonId() == -1) {
            checkIsValid = 0;
        }

        return checkIsValid;
    }

    /**
     * This function compares responses to correct answers and updates score
     * @param checkbox1 holds the specific checkbox being validated q1
     * @param checkbox2 holds the specific checkbox being validated q1
     * @param checkbox3 holds the specific checkbox being validated q1
     * @param radioGroup2 holds the specific radio group being validated q2
     * @param radioGroup3 holds the specific radio group being validated q3
     * @param radioGroup4 holds the specific radio group being validated q4
     * @return turns 1 (valid) or 0 (not valid)
     */

    public int verifyForm(CheckBox checkbox1, CheckBox checkbox2, CheckBox checkbox3, RadioGroup radioGroup2, RadioGroup radioGroup3, RadioGroup radioGroup4) {
        int isVerified = 1;
        boolean box1 = checkbox1.isChecked();
        boolean box2 = checkbox2.isChecked();
        boolean box3 = checkbox3.isChecked();

        /** (box1 checked AND box2 checked AND box3 not checked */

        if (box1 && box2 && !box3) {
            score++;  // increment score for correct answer
        } else {
            isVerified = 0;
        }
        if (verifyAnswers(radioGroup2, 0) == 0) {
            isVerified = 0;
        } else {
            score++;  // increment score for correct answer
        }

        if (verifyAnswers(radioGroup3, 2) == 0) {
            isVerified = 0;
        } else {
            score++;  // increment score for correct answer
        }
        if (verifyAnswers(radioGroup4, 0) == 0) {
            isVerified = 0;
        } else {
            score++;  // increment score for correct answer
        }

        /** no incorrect answer to free text field, thus no verification on group5 */


        return isVerified;
    }

    /**
     * This function verifies specifically the radioGroups (q2, q3, q4)
     * @param radioGroup holds the specific radio group being verified
     * @param ans        holds the number of the correct answer
     * @return turns 1 (verified) or 0 (not verified)
     */

    public int verifyAnswers(RadioGroup radioGroup, int ans) {
        int checkIsVerified = 1;

        /** verify the passed radio group has an answer */

        View g = radioGroup.getChildAt(ans);
        if (g instanceof RadioButton && !((RadioButton) g).isChecked()) {
            checkIsVerified = 0;
        }

        return checkIsVerified;
    }

    /**
     * This function controls validation and verification of quiz answers
     * then displays messages and score
     * @param view is the default view
     */

    public void submitAnswers(View view) {
        score = 0; /* reset score */
        /** define the variables that hold the answers */

        /** group_1 is checkboxes */
        CheckBox checkbox1 = (CheckBox) findViewById(R.id.checkbox1);
        CheckBox checkbox2 = (CheckBox) findViewById(R.id.checkbox2);
        CheckBox checkbox3 = (CheckBox) findViewById(R.id.checkbox3);

        /** group 2 - 4 are radio buttons */
        RadioGroup radioGroup2 = (RadioGroup) findViewById(R.id.group_2);
        RadioGroup radioGroup3 = (RadioGroup) findViewById(R.id.group_3);
        RadioGroup radioGroup4 = (RadioGroup) findViewById(R.id.group_4);

        /** group 5 is a text field */
        EditText group5 = (EditText) findViewById(R.id.group_5);
        String text = group5.getText().toString();

        /** check that all quiz questions were answered
         * and return boolean */

        int isValid = validateForm(checkbox1, checkbox2, checkbox3, radioGroup2, radioGroup3, radioGroup4, text);
        int isVerified = 0;

        if (isValid == 1) {

            /** check that all quiz questions were answered *correctly*
             * and return boolean */

            isVerified = verifyForm(checkbox1, checkbox2, checkbox3, radioGroup2, radioGroup3, radioGroup4);

        } else {
            Toast.makeText(this, "Please answer all questions before checking your score!", Toast.LENGTH_SHORT).show();
            return;
        }

        displayScore(score, isValid, isVerified);

    }
    private void displayScore(int score, int isValid, int isVerified) {
        TextView theScore = (TextView) findViewById(R.id.scoreNum);
        theScore.setText("" + score);

        if (isValid == 1 && isVerified == 1) {
            Toast.makeText(this, "Congratulations! All " + score + " answers are correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Sorry, only " + score + " answers are correct." , Toast.LENGTH_SHORT).show();
        }
    }
}

