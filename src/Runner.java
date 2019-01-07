public class Runner {
    int xPosition;
    int yPosition;
    String heading = "north";
    public void decideWhichWay(int[][] maze){
        switch (heading){
            case "north": if (maze[yPosition - 1][xPosition] == 1) {
                this.yPosition--;
                break;
            }else{
                heading="east";
            }
            case "east": if(heading=="east") {
                if (maze[yPosition][xPosition + 1] == 1) {
                    this.xPosition++;
                    break;
                }
            }else{
                heading="south";
            }
            case "south": if (maze[yPosition + 1][xPosition] == 1) {
                this.yPosition++;
                break;
            }else{
                heading="west";
            }
            case "west": if (maze[yPosition][xPosition - 1] == 1) {
                this.xPosition--;
                break;
            }else{
                heading="north";
            }

        }

    }
    public Runner(int xPosition, int yPosition){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }
}
