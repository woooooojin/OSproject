package application;
import java.net.URL;
import java.util.LinkedList;
import java.util.Locale.Category;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Stream;
import com.sun.javafx.charts.Legend;
import com.sun.javafx.charts.Legend.LegendItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class Controller implements Initializable {
	//GUI ��Ʈ�� ����
	@FXML
	private ComboBox comSchedule;
	@FXML
	private ComboBox comPro;
	
	@FXML
	private Button btnConfirm;
	@FXML
	private Button btnSet;
	@FXML
	private Button btnStart;
	@FXML
	private Button btnClear;
	
	@FXML
	private TextField textTimeQ;
	@FXML
	private TextField textNumP;
	
	@FXML
	private TextField textAT;
	@FXML
	private TextField textBT;
	
	@FXML
	private TextArea textArea;
	
	@FXML
	private StackedBarChart chart;
	
	@FXML
	private CategoryAxis yAxis;
	private CategoryAxis xAxis;
	
	//�޺��ڽ� �޴� ����Ʈ
	ObservableList<String> scheduleList = FXCollections
			.observableArrayList("FCFS","RR","SPN","SRTN","HRRN");
	ObservableList<String> processList = FXCollections
			.observableArrayList("Process P1");
	
	//����ڿ��� ���μ���, �����ٸ� ���� ���� �Է¹��� �ν��Ͻ� ���� 
	int numP; int timequantum = 0; String nameSchedule ="FCFS";
	
	//��Ʈ �÷� ���� ����
	Node nodeColor; String[] rgb;
    Color[] color = {Color.BLUE, Color.RED, Color.LIGHTGREEN, Color.BEIGE, Color.BURLYWOOD};
    
	ProcessP[] ap;
    String textPro;
    LinkedList<ProcessP> lp = new LinkedList<ProcessP>();
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String s;
	      
	    int[] aNumbers;
	    int[] bNumbers;
		
		yAxis.getCategories().addAll("Process");
		ObservableList<XYChart.Series<Number,Category>> list = FXCollections.observableArrayList();

		comSchedule.setValue("FCFS");  //�����ٸ� ��� �ʱ� ���� ��
		comSchedule.setItems(scheduleList);  //�޺��ڽ��� �����ٸ� ����Ʈ ����
		
	    textArea.setText("-> Hello! Scheduling Program!\n\r\n\r");
	    textArea.setText(textArea.getText() + "-> Please Setting Scheduling Information :-)\n\r\n\r");
	    
	    //�޺��ڽ� �̺�Ʈ ó�� - �����ٸ� ����
	  	comSchedule.valueProperty().addListener(new ChangeListener<String>() {
	  		@Override public void changed(ObservableValue ov, String before, String temp) {
	  			nameSchedule = temp;  //���� ������ �����ٸ� ��� ����
	  				
	  			//������ �����ٸ� ����� RR�� ��� timeQuantum �Է� �ؽ�Ʈ�ʵ� Ȱ��ȭ
	  			if(nameSchedule.equals("RR")) textTimeQ.setDisable(false);
	  			else textTimeQ.setDisable(true);
	  		}
	  	});
	  		
	  	//�޺��ڽ� �̺�Ʈ ó�� - ���μ��� ����
	  	comPro.valueProperty().addListener(new ChangeListener<String>() {
	  		@Override public void changed(ObservableValue ov, String before, String temp) {
	  			textPro = temp; //���� ������ ���μ��� ���� (ex : Process P1)
	  		}
	  	});
	  	
	  	//��ư �̺�Ʈ ó�� - monitorClear ��ư
	    btnClear.setOnAction(event -> {
	    	textArea.clear();
	    });
	    
	  	//��ư �̺�Ʈ ó�� - Comfirm ��ư
	    btnConfirm.setOnAction(event -> {
	     	//���μ��� �� ���� �� ���μ��� ����
			
	    	if(!textNumP.getText().toString().equals("")) {
	    		numP = Integer.parseInt(textNumP.getText().toString());
				ap = new ProcessP[numP];
				//���μ��� ����迭
				rgb = new String[numP+1];
				
				for(int i=0;i<numP;i++) {
					//���μ��� ����
			        ap[i] = new ProcessP();
			        
			        //���μ��� ���� ���� ����
			        rgb[i+1] = String.format("%d, %d, %d",
					            (int) (color[i%5].getRed() * Math.floor( (255/numP)*(i+1) )),
					            (int) (color[i%5].getGreen() * Math.floor( (255/numP)*(i+1) )),
					            (int) (color[i%5].getBlue() * Math.floor( (255/numP)*(i+1) )));
			    } 
	    	}
	
			//���μ��� ����� RR�� ��� TimeQuantum�� ����
	    	if(nameSchedule.equals("RR") && !textTimeQ.getText().toString().equals("")) {
	    		timequantum = Integer.parseInt(textTimeQ.getText().toString());
	    	}
	    	
			//���μ��� �޺��ڽ� �ʱⰪ ���� 
			comPro.getItems().clear();
			comPro.setValue("Process P1");
			
			//���μ��� �� ��ŭ �޺��ڽ� ����Ʈ �߰�
			for(int i = 1; i <= numP; i++) {
				comPro.getItems().add("Process P"+Integer.toString(i));
			}
			
			//����Ϳ� ������ ��� ���
			textArea.clear();
			textArea.setText(textArea.getText() + "-> Process Scheduling : " + nameSchedule + "!\n\r\n\r");
			textArea.setText(textArea.getText() + "-> Create Process " + Integer.toString(numP) + "!\n\r\n\r");
			if(nameSchedule.equals("RR")) textArea.setText(textArea.getText() + "-> Time Quantum " + timequantum + "!\n\r\n\r");
			textArea.setText(textArea.getText() + "-> Please Setting Process Information :-)\n\r\n\r");
			
			textTimeQ.clear();
			textNumP.clear();
		});
		
	    //��ư �̺�Ʈ ó�� - Set��ư
		btnSet.setOnMouseClicked(event -> {
			//���� �Է¹޾ƿ� ���μ������� ���ںκи� ����
			Scanner in = new Scanner(textPro).useDelimiter("[^0-9]+");
			int index = in.nextInt() - 1;
			System.out.println(index);
			
			//���μ�����  �Է¹��� AT, BT, RT �ʱⰪ ����
			ap[index].setAT(Integer.parseInt(textAT.getText().toString()));
			ap[index].setBT(Integer.parseInt(textBT.getText().toString()));
			ap[index].setRT(ap[index].getBT());
			
		    //���� ���μ����� ���ؼ� ���� ���Է��ϸ� ���� ���� ����
	        Predicate<ProcessP> processPredicate = p-> p.getPid() == (index+1);
	        lp.removeIf(processPredicate);
	        lp.add(ap[index]);
			
			//����Ϳ� ������ ��� ���
			textArea.setText(textArea.getText() + "-> Set Process " + (index + 1));
			textArea.setText(textArea.getText() + "  AT : " + ap[index].getAT());
			textArea.setText(textArea.getText() + "  BT : " + ap[index].getBT() + "\n\r\n\r");
			
			textAT.clear();
			textBT.clear();
		});
		
		//��ư �̺�Ʈ ó�� - Start��ư
		btnStart.setOnMouseClicked(event -> {
			try {
				//���μ��� ������ �����ٸ� ���� �Է� ������ ��� ����ó��
				if(lp == null && timequantum == 0){
					throw new IndexOutOfBoundsException();
				}else {
						
					//���μ��� ����Ʈ�� �����Ͽ� �����ٸ� ����
					Scheduling sched = new Scheduling(lp);
				    switch(nameSchedule) {
				      case "FCFS":sched.FCFS();  break;
				      case "RR": sched.RR(timequantum);break;
				      case "SPN": sched.SPN(); break;
				      case "SRTN": sched.SRTN(); break;
				      case "HRRN": sched.HRRN(); break;
				      default:     
				    }
				    
				    int size = sched.T.size();
				    
				    //�����ٸ� ��� ��Ʈ �׸���
				    chart.getData().clear();
				    
				    XYChart.Series<Number,Category>[] CategoryP = Stream.<XYChart.Series<String, Number>>
				    	generate(XYChart.Series::new).limit(size).toArray(XYChart.Series[]::new);
				    for(int i = 0; i < size;i++) {
				    	CategoryP[i].getData().add(new XYChart.Data(sched.T.get(i), "Process"));
				    	
				    	if(i<numP) CategoryP[i].setName("Process" + (i+1));
				    	else CategoryP[i].setName("Remove");
				    	
				    	list.add(CategoryP[i]);
				    }
				    chart.setData(list);
				    
				    //��Ʈ series ���� ����
				    for(int i = 0; i < size; i++) { 
				    	for( Node n : CategoryP[i].getChart().lookupAll(".series"+i)) {
						   n.getStyleClass().remove("default-color"+(i%8));
						   n.getStyleClass().add("default-color"+(i%50));
						   n.setStyle("-fx-bar-fill: rgba(" + rgb[sched.P.get(i)] + ", 0.5);");
				    	}	
					}
				    
				    //��Ʈ ���� ���� ����
				    Legend legend = (Legend)CategoryP[0].getChart().lookup(".chart-legend");
				    legend.getItems().removeIf(item->item.getText().equals("Remove"));
		
				    //��Ʈ ���� ���� ����
				    int i = 0;
				    for( Node n : CategoryP[i].getChart().lookupAll(".chart-legend-item")) {
			    		if (n instanceof Label && ((Label) n).getGraphic() != null && i < numP) {
						   ((Label) n).getGraphic().getStyleClass().remove("default-color" + (i % 8));
				           ((Label) n).getGraphic().getStyleClass().add("default-color" + (i % 50));
				           ((Label) n).getGraphic().setStyle("-fx-background-color: rgba(" + rgb[i+1] + ", 0.5);");
			    		}
			    		i++;
			    	}
				    
				    //����Ϳ� �����ٸ� ��� ���
				    textArea.clear();
				    textArea.setText(textArea.getText() + "-> Complete!!! Process Scheduling : " + nameSchedule + "!\n\r\n\r");
				    textArea.setText(textArea.getText() + sched.toString() + "\n\r\n\r");
				}
			}catch(IndexOutOfBoundsException e) {  //���μ��� ����, �����ٸ� ���� �Է� ������ ��� ����ó��
				chart.getData().clear();
				textArea.clear();
				textArea.setText(textArea.getText() + "-> error : Please retry setting Schduling info and Process info!\n\r\n\r");
			}
		});
	}
}
