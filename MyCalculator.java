import java.awt.*;
import java.awt.event.*;

public class MyCalculator extends Frame{
public boolean setClear=true;
double number, memValue;
char op;
String digitButtonText[] = {"7", "8", "9", "4", "5", "6", "1", "2", "3", "0", "+/-", "." };
String operatorButtonText[] = {"/", "√", "*", "%", "-", "1/X", "+", "=" };
String memoryButtonText[] = {"MC", "MR", "MS", "M+","M-" };
String specialButtonText[] = {"Del", "C", "CE", };

MyDigitButton digitButton[]=new MyDigitButton[digitButtonText.length];
MyOperatorButton operatorButton[]=new MyOperatorButton[operatorButtonText.length];
MyMemoryButton memoryButton[]=new MyMemoryButton[memoryButtonText.length];
MySpecialButton specialButton[]=new MySpecialButton[specialButtonText.length];

Label displayLabel=new Label("0",Label.RIGHT);
Label memLabel=new Label(" ",Label.RIGHT);

final int FRAME_WIDTH=235,FRAME_HEIGHT=350;
final int HEIGHT=40, WIDTH=35, H_SPACE=1,V_SPACE=1;
final int TOPX=10, TOPY=60;

MyCalculator(String frameText)
{
super(frameText);
// SA DISPLAY NA PART CHUCHU 0
int tempX = TOPX, y = TOPY;
int displayWidth = 215;
displayLabel.setBounds(tempX, y, displayWidth, HEIGHT);
displayLabel.setBackground(Color.GRAY);
displayLabel.setForeground(Color.WHITE);
// Adjust the font size sa 0
Font displayFont = new Font("Arial", Font.BOLD, 20);
displayLabel.setFont(displayFont);

add(displayLabel);
memLabel.setBounds(TOPX,  TOPY+HEIGHT+ V_SPACE,WIDTH, HEIGHT);
add(memLabel);

//memory buttons
tempX=TOPX;
y=TOPY+2*(HEIGHT+V_SPACE);
for(int i=0; i<memoryButton.length; i++)
{
memoryButton[i]=new MyMemoryButton(tempX,y,WIDTH,HEIGHT,memoryButtonText[i], this);
memoryButton[i].setForeground(Color.WHITE);
y+=HEIGHT+V_SPACE;
}
// special buttons
tempX = TOPX + WIDTH + H_SPACE;
y = TOPY + 2 * (HEIGHT + V_SPACE);
for (int i = 0; i < specialButton.length; i++) {
    specialButton[i] = new MySpecialButton(tempX, y, WIDTH, HEIGHT, specialButtonText[i], this);
 // Adjust the width for the DEL button
    if (i == 0) {
        specialButton[i].setBounds(tempX, y, WIDTH *3, HEIGHT);
        tempX = tempX + WIDTH * 3 + H_SPACE;
    } else {
        tempX = tempX + WIDTH + H_SPACE;
    }
}
//ditigt buttons
int digitX=TOPX+WIDTH+H_SPACE;
int digitY=TOPY+3*(HEIGHT+V_SPACE);
tempX=digitX;
y=digitY;
for(int i=0;i<digitButton.length;i++)
{
digitButton[i]=new MyDigitButton(tempX,y,WIDTH,HEIGHT,digitButtonText[i], this);
digitButton[i].setForeground(Color.WHITE);
tempX+=WIDTH+H_SPACE;
if((i+1)%3==0){tempX=digitX; y+=HEIGHT+V_SPACE;}
}
//opearators button
int opsX=digitX+2*(WIDTH+H_SPACE)+H_SPACE;
int opsY=digitY;
tempX=opsX;
y=opsY;
for(int i=0;i<operatorButton.length;i++)
{
tempX+=WIDTH+H_SPACE;
operatorButton[i]=new MyOperatorButton(tempX,y,WIDTH,HEIGHT,operatorButtonText[i], this);
operatorButton[i].setForeground(Color.WHITE);
if((i+1)%2==0){
	tempX=opsX; y+=HEIGHT+V_SPACE;}
}

addWindowListener(new WindowAdapter()
{
public void windowClosing(WindowEvent ev)
{System.exit(0);}
});
setLayout(null);
setSize(FRAME_WIDTH,FRAME_HEIGHT);
//BACKGROUND COLOR
setBackground(Color.GRAY);
setVisible(true);
}
static String getFormattedText(double temp) {
        String resText = "" + temp;
        if (resText.lastIndexOf(".0") > 0) {
            resText = resText.substring(0, resText.length() - 2);
        }
        return resText;
}
public static void main(String []args)
{
new MyCalculator("Calculator - JavaTpoint");
}
}
//class DigitButton
class MyDigitButton extends Button implements ActionListener
{
MyCalculator cl;
MyDigitButton(int x,int y, int width,int height,String cap, MyCalculator clc)
{
super(cap);
setBounds(x,y,width,height);
this.cl=clc;
this.cl.add(this);
addActionListener(this);
// sa 12345.. na part
setBackground(Color.BLACK);
 setForeground(Color.WHITE);
}

static boolean isInString(String s, char ch)
{
for(int i=0; i<s.length();i++) if(s.charAt(i)==ch) return true;
return false;
}

public void actionPerformed(ActionEvent ev)
{
 String tempText = ((MyDigitButton) ev.getSource()).getLabel();
	 if (tempText.equals(".")) {
        if (cl.setClear) {
            cl.displayLabel.setText("0.");
            cl.setClear = false;
  } else if (!isInString(cl.displayLabel.getText(), '.')) {
            cl.displayLabel.setText(cl.displayLabel.getText() + ".");
        }
        return;
}
// sa +/- na funcion  or the negation part //
 if (tempText.equals("+/-")) {
        double currentValue = Double.parseDouble(cl.displayLabel.getText());
        currentValue = -currentValue;
        cl.displayLabel.setText(MyCalculator.getFormattedText(currentValue));
        return;
    }
int index=0;
try{
        index=Integer.parseInt(tempText);
    }catch(NumberFormatException e){return;}

if (index==0 && cl.displayLabel.getText().equals("0")) return;

if(cl.setClear)
        	{cl.displayLabel.setText(""+index);cl.setClear=false;}
else
	cl.displayLabel.setText(cl.displayLabel.getText()+index);
}
}

//class Operator Button
class MyOperatorButton extends Button implements ActionListener {
    MyCalculator cl;

    MyOperatorButton(int x, int y, int width, int height, String cap, MyCalculator clc) {
        super(cap);
        setBounds(x, y, width, height);
        this.cl = clc;
        this.cl.add(this);
        addActionListener(this);
        //sa +,-,*.. na part//
    setBackground(Color.DARK_GRAY);
        setForeground(Color.WHITE);
    }

    public void actionPerformed(ActionEvent ev) {
        String opText = ((MyOperatorButton) ev.getSource()).getLabel();

        cl.setClear = true;
        double temp = Double.parseDouble(cl.displayLabel.getText());

        if (opText.equals("1/X")) {
            try {
                double tempd = 1 / (double) temp;
                cl.displayLabel.setText(MyCalculator.getFormattedText(tempd));
            } catch (ArithmeticException excp) {
                cl.displayLabel.setText("Divide by 0.");
            }
            return;
        }
        if (opText.equals("√")) {
            try {
                double tempd = Math.sqrt(temp);
                cl.displayLabel.setText(MyCalculator.getFormattedText(tempd));
            } catch (ArithmeticException excp) {
                cl.displayLabel.setText("Divide by 0.");
            }
            return;
        }
        if (opText.equals("%")) {
            temp /= 100;
            cl.displayLabel.setText(MyCalculator.getFormattedText(temp));
            return;
        }
        if (!opText.equals("=")) {
            cl.number = temp;
            cl.op = opText.charAt(0);
            return;
        }
        switch (cl.op) {
            case '+':
                temp += cl.number;
                break;
            case '-':
                temp = cl.number - temp;
                break;
            case '*':
                temp *= cl.number;
                break;
            case '/':
                try {
                    temp = cl.number / temp;
                } catch (ArithmeticException excp) {
                    cl.displayLabel.setText("Divide by 0.");
                    return;
                }
                break;
        }
        cl.displayLabel.setText(MyCalculator.getFormattedText(temp));
    }
}



//class MemoryButton
class MyMemoryButton extends Button implements ActionListener
{
MyCalculator cl;


MyMemoryButton(int x,int y, int width,int height,String cap, MyCalculator clc)
{
super(cap);
setBounds(x,y,width,height);
this.cl=clc;
this.cl.add(this);
addActionListener(this);
// sa MC,MR,MS.. na part//
 setBackground(Color.DARK_GRAY);
   setForeground(Color.WHITE);
}

public void actionPerformed(ActionEvent ev)
{
char memop=((MyMemoryButton)ev.getSource()).getLabel().charAt(1);

cl.setClear=true;
double temp=Double.parseDouble(cl.displayLabel.getText());

switch(memop)
{
case 'C'://MC
	cl.memLabel.setText(" ");cl.memValue=0.0;break;
case 'R': //MR
	cl.displayLabel.setText(MyCalculator.getFormattedText(cl.memValue));break;
case 'S': //MS
	cl.memValue=0.0;
case '+': //M+
	cl.memValue+=Double.parseDouble(cl.displayLabel.getText());
	if(cl.displayLabel.getText().equals("0") || cl.displayLabel.getText().equals("0.0")  )
		cl.memLabel.setText(" ");
	else
		cl.memLabel.setText("");
	break;
	case '-': // Added handling for M-
	  cl.memValue -= Double.parseDouble(cl.displayLabel.getText());
	    if (cl.displayLabel.getText().equals("0") || cl.displayLabel.getText().equals("0.0"))
	           cl.memLabel.setText(" ");
	       else
	          cl.memLabel.setText("");
   break;

		}
	}
}
//Class SpecialButton
class MySpecialButton extends Button implements ActionListener
{
MyCalculator cl;

MySpecialButton(int x,int y, int width,int height,String cap, MyCalculator clc)
{
super(cap);
setBounds(x,y,width,height);
this.cl=clc;
this.cl.add(this);
addActionListener(this);
//sa Delete, C, CE na part//
setBackground(Color.DARK_GRAY);
  setForeground(Color.WHITE);
}

static String Delete(String s)
{
String Res="";
for(int i=0; i<s.length()-1; i++) Res+=s.charAt(i);
return Res;
}
public void actionPerformed(ActionEvent ev)
{
String opText=((MySpecialButton)ev.getSource()).getLabel();

if(opText.equals("Del"))
{
String tempText=Delete(cl.displayLabel.getText());
if(tempText.equals(""))
	cl.displayLabel.setText("0");
else
	cl.displayLabel.setText(tempText);
return;
}

if(opText.equals("C"))
{
cl.number=0.0; cl.op=' '; cl.memValue=0.0;
cl.memLabel.setText(" ");
}

cl.displayLabel.setText("0");cl.setClear=true;
		}
	}