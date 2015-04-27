package chess.pieces;

import java.util.HashSet;
import java.util.Set;

import chess.GameState;
import chess.Player;
import chess.Position;

/**
 * The 'Rook' class
 */
public class Rook extends Piece {

	// These variables help us to identify when to stop searching in a particular direction.
	// i.e. if the position is out of board or if there is a collision with opposite color player
	boolean canMoveUp, canMoveDown, canMoveLeft, canMoveRight;

	public Rook(Player owner) {
		super(owner);
	}

	@Override
	protected char getIdentifyingCharacter() {
		return 'r';
	}

	/**@author Amit
	 * Return a set of all valid moves for a Rook
	 * @param currentState - The current state of the game
	 * @param currentPosition - The current position of the Rook
	 * @return Set of valid positions
	 */
	@Override
	public Set<Position> getValidMoves(GameState currentState, Position currentPosition) {
		//This set would contain a list of all valid positions
		Set<Position> allValidPositions = new HashSet<Position>();    	
		// Set all Directions to True
		setDirectionsTrue();

		for(int i=1; i<=8; i++){    		
			// possible moves for Rook to go UP
			allValidPositions.addAll(getMovesForOffset(currentState, currentPosition, 0, i));
			// possible moves for Rook to go DOWN
			allValidPositions.addAll(getMovesForOffset(currentState, currentPosition, 0, -i));
			// possible moves for Rook to go LEFT
			allValidPositions.addAll(getMovesForOffset(currentState, currentPosition, -i, 0));
			// possible moves for Rook to go RIGHT
			allValidPositions.addAll(getMovesForOffset(currentState, currentPosition, i, 0));

			if((canMoveLeft == false) && (canMoveRight == false) && (canMoveUp == false) && (canMoveDown == false))
				break;
		}    	
		return allValidPositions;
	}

	/**@author Amit
	 * Return a set of all possible moves for given direction for a Rook
	 * @param currentState The current state of the game
	 * @param currentPosition The current position of the Bishop
	 * @param xOffset - movement on X Axis
	 * @param YOffset - movement on Y Axis
	 * @return Set of valid positions in given direction determined by Offset
	 */
	private Set<Position> getMovesForOffset(GameState currentState, Position currentPosition, int xOffset, int yOffset){
		Set<Position> validOffsetPositions = new HashSet<Position>();

		if( Position.isOnBoard((char)((int)currentPosition.getColumn()+xOffset), currentPosition.getRow()+yOffset) 
				&& canMoveInDirection(xOffset, yOffset)){	
			if((currentState.getPieceAt(new Position((char)((int)currentPosition.getColumn()+xOffset), currentPosition.getRow()+yOffset)) == null )){
				Position newPosition = new Position((char)((int)currentPosition.getColumn()+xOffset), currentPosition.getRow()+yOffset);
				validOffsetPositions.add(newPosition);									
			}else if(currentState.getPieceAt(new Position((char)((int)currentPosition.getColumn()+xOffset), currentPosition.getRow()+yOffset)).getOwner() != currentState.getPieceAt(currentPosition).getOwner()){
				Position newPosition = new Position((char)((int)currentPosition.getColumn()+xOffset), currentPosition.getRow()+yOffset);
				validOffsetPositions.add(newPosition);
				setDirectionFalse(xOffset, yOffset);
			}else
				setDirectionFalse(xOffset, yOffset);			
		}else{
			setDirectionFalse(xOffset, yOffset);
		}
		return validOffsetPositions;
	}

	/**@author Amit
	 * Set the value for a particular direction depending on Offset
	 * @param xOffset - movement on X Axis
	 * @param YOffset - movement on  Axis
	 */
	private void setDirectionFalse(int xOffset, int yOffset){

		if(Math.signum(xOffset) == 0){
			//UP
			if(Math.signum(yOffset) == 1){
				this.canMoveUp = false;
			}else{ //Down
				this.canMoveDown = false;
			}
		}else{		
			//RIGHT
			if(Math.signum(xOffset) == 1){
				this.canMoveRight = false;
			}else{ //LEFT
				this.canMoveLeft = false;
			}			

		}
	}

	/**@author Amit
	 * Check if the Rook can move in a direction defined by offsets
	 * @param xOffset - movement on X Axis
	 * @param YOffset - movement on Y Axis
	 * @return True if it can move in the given direction else return False
	 */
	private boolean canMoveInDirection(int xOffset, int yOffset){

		if(Math.signum(xOffset) == 0){
			//UP
			if(Math.signum(yOffset) == 1){
				return this.canMoveUp;
			}else{ //Down
				return this.canMoveDown;
			}
		}else{		
			//RIGHT
			if(Math.signum(xOffset) == 1){
				return this.canMoveRight;
			}else{ //LEFT
				return this.canMoveLeft;
			}			

		}
	}

	/**@author Amit
	 * Set all the directions that Rook can move to true.
	 */
	private void setDirectionsTrue(){
		this.canMoveDown = true;
		this.canMoveLeft = true;
		this.canMoveRight = true;
		this.canMoveUp = true;
	}

}
