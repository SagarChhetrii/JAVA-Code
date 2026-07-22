public class check_un {
	public static void main(String[] args) {
		try {
			checkedExceptionDemo();
		} catch (Exception e) {
			System.out.println("Checked exception caught: " + e.getMessage());
		}

		try {
			uncheckedExceptionDemo();
		} catch (RuntimeException e) {
			System.out.println("Unchecked exception caught: " + e.getMessage());
		}
	}

	static void checkedExceptionDemo() throws Exception {
		throw new Exception("This is a checked exception");
	}

	static void uncheckedExceptionDemo() {
		throw new RuntimeException("This is an unchecked exception");
	}
}
