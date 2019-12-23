package loop;

public class BreakTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		go: for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				System.out.println("i==" + i + ",j==" + j);
				if(j == 2)break go;
			}
		}
		
		int i = 0;
		go: while(true) {
			i++;
			int j = 0;
			while(true) {
				System.out.println("i==" + i + ",j==" + j++);
				if(j == 3)break go;
			}
		}
	}

}
