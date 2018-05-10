public class LabyrinthVersion {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int numberOfQueries = scan.nextInt();
		while (numberOfQueries-- > 0) {
			int sizeOfArray = scan.nextInt();
			int leap = scan.nextInt();

			int[] game = new int[sizeOfArray];
			for (int i = 0; i < sizeOfArray; i++) {
				game[i] = scan.nextInt();
			}

			System.out.println((canWin(leap, game)) ? "YES" : "NO");
		}
		scan.close();
	}

	/**
	 * It is important that the steps represented by the if statements are
	 * implemented exactly in the same order so the we avoid both an infinite loop
	 * or positions that remained unexplored.
	 */
	public static boolean canWin(int leap, int[] game) {

		boolean result = true;
		/*
		 * A set to mark the positions over which we walked backward so that we do not
		 * walk forward on the same positions and thus make the loop infinite.
		 */
		Set<Integer> walkedBackeardsPositions = new HashSet<Integer>();
		int currentPosition = 0;
		/*
		 * A LinkedList to mark the previous positions from which leaps were made. It
		 * serves two purposes: 1. When there is no possibility to leap or walk
		 * forward/backward from the current position we return to the position from
		 * which we made the leap and try to walk forward/backward to a position from
		 * which we can leap. 2. In order to avoid an infinite loop, we can leap only
		 * from a position that is not contained in the list .
		 */
		LinkedList<Integer> previousIndexesBeforeLeap = new LinkedList<Integer>();
		int startIndexOfPreviousLeap = 0;

		while (true) {

			if (leap + currentPosition > game.length - 1) {
				result = true;
				break;
			} else if (leap + currentPosition == game.length - 1 && game[leap + currentPosition] == 0) {
				result = true;
				break;
			} else if (leap + currentPosition < game.length - 1 && game[leap + currentPosition] == 0
					&& !previousIndexesBeforeLeap.contains(currentPosition)) {
				previousIndexesBeforeLeap.add(currentPosition);
				currentPosition += leap;
			} else if (1 + currentPosition == game.length - 1 && game[currentPosition + 1] == 0) {
				result = true;
				break;
			} else if (1 + currentPosition < game.length - 1 && game[currentPosition + 1] == 0
					&& !walkedBackeardsPositions.contains(currentPosition)) {
				currentPosition++;
			} else if (currentPosition - 1 >= 0 && game[currentPosition - 1] == 0) {
				currentPosition--;
				walkedBackeardsPositions.add(currentPosition);
			} else if ((previousIndexesBeforeLeap.size() - 1 - startIndexOfPreviousLeap) >= 0) {
				currentPosition = previousIndexesBeforeLeap
						.get(previousIndexesBeforeLeap.size() - 1 - startIndexOfPreviousLeap);
				startIndexOfPreviousLeap++;
			} else {
				result = false;
				break;
			}
		}
		return result;
	}

}
