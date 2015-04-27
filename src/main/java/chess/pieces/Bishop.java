package chess.pieces;

import java.util.HashSet;
import java.util.Set;

import chess.GameState;
import chess.Player;
import chess.Position;

/**
 * The 'Bishop' class
 */
public class Bishop extends Piece {

	// These variables help us to identify when to stop searching in a particular direction.
	// i.e. if the position is out of board or if there is a collision with opposite color player
	private boolean canMoveNorthEast;
	private boolean canMoveNorthWest;
	private boolean canMoveSouthEast;
	private boolean canMoveSouthWest;

	public Bishop(Player owner) {
		super(owner);		
	}

	@Override
	protected char getIdentifyingCharacter() {
		return 'b';
	}

	/**@author Amit
	 * Return a set of all possible moves for a Bishop
	 * @param currentState The current state of the game
	 * @param currentPosition The current position of the Bishop
	 * @return Set of valid positions
	 */
	@Override
	public Set<Position> getValidMoves(GameState currentState, Position currentPosition) {
		//This set would contain a list of all valid positions
		Set<Position> allValidPositions = new HashSet<Position>();
		// Set all Directions to True
		setDirectionsTrue();

		for(int i=1; i<=8; i++){    		
			// Valid locations in North east
			allValidPositions.addAll(getMovesForOffset(currentState, currentPosition, i, i));
			// Valid locations in North west
			allValidPositions.addAll(getMovesForOffset(currentState, currentPosition, -i, i));
			// Valid locations in South east
			allValidPositions.addAll(getMovesForOffset(currentState, currentPosition, i, -i));
			// Valid locations in South West
			allValidPositions.addAll(getMovesForOffset(currentState, currentPosition, -i, -i));

			// Break the loop if the bishop cannot move in any direction
			if(this.canMoveNorthEast == false && this.canMoveNorthWest == false 
					&& this.canMoveSouthEast == false && this.canMoveSouthWest == false){
				break;
			}
		}	
		return allValidPositions;	
	}	

	/**@author Amit
	 * Return a set of all possible moves for given direction for a Bishop
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
	 * @param YOffset - movement on Y Axis
	 */
	private void setDirectionFalse(int xOffset, int yOffset){

		if(Math.signum(xOffset) == 1){
			//North East
			if(Math.signum(yOffset) == 1){
				this.canMoveNorthEast = false;
			}else{ //South East
				this.canMoveSouthEast = false;
			}
		}else{		
			//North West
			if(Math.signum(yOffset) == 1){
				this.canMoveNorthWest = false;
			}else{ //South West
				this.canMoveSouthWest = false;
			}			

		}
	}

	/**@author Amit
	 * Check if the Bishop can move in a direction defined by offsets
	 * @param xOffset - movement on X Axis
	 * @param YOffset - movement on  Axis
	 * @return True if it can move in the given direction else return False
	 */
	private boolean canMoveInDirection(int xOffset, int yOffset){

		if(Math.signum(xOffset) == 1){
			//North East
			if(Math.signum(yOffset) == 1){
				return this.canMoveNorthEast;
			}else{ //South East
				return this.canMoveSouthEast;
			}
		}else{		
			//North West
			if(Math.signum(yOffset) == 1){
				return this.canMoveNorthWest;
			}else{ //South West
				return this.canMoveSouthWest;
			}			
		}
	}

	/**@author Amit
	 * Set all the directions that bishop can move to true.
	 */
	private void setDirectionsTrue(){
		this.canMoveNorthEast = true;
		this.canMoveNorthWest = true;
		this.canMoveSouthEast = true;
		this.canMoveSouthWest = true;
	}
}
