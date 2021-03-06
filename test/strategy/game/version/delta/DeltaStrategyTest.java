/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package strategy.game.version.delta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static strategy.common.PlayerColor.BLUE;
import static strategy.common.PlayerColor.RED;
import static strategy.game.common.PieceType.BOMB;
import static strategy.game.common.PieceType.CHOKE_POINT;
import static strategy.game.common.PieceType.FLAG;
import static strategy.game.common.PieceType.LIEUTENANT;
import static strategy.game.common.PieceType.MARSHAL;
import static strategy.game.common.PieceType.MINER;
import static strategy.game.common.PieceType.SCOUT;
import static strategy.game.common.PieceType.SERGEANT;
import static strategy.game.common.PieceType.SPY;
import static strategy.game.version.testutil.TestLocations.badLoc;
import static strategy.game.version.testutil.TestLocations.loc00;
import static strategy.game.version.testutil.TestLocations.loc01;
import static strategy.game.version.testutil.TestLocations.loc02;
import static strategy.game.version.testutil.TestLocations.loc03;
import static strategy.game.version.testutil.TestLocations.loc04;
import static strategy.game.version.testutil.TestLocations.loc05;
import static strategy.game.version.testutil.TestLocations.loc12;
import static strategy.game.version.testutil.TestLocations.loc13;
import static strategy.game.version.testutil.TestLocations.loc14;
import static strategy.game.version.testutil.TestLocations.loc15;
import static strategy.game.version.testutil.TestLocations.loc16;
import static strategy.game.version.testutil.TestLocations.loc17;
import static strategy.game.version.testutil.TestLocations.loc22;
import static strategy.game.version.testutil.TestLocations.loc23;
import static strategy.game.version.testutil.TestLocations.loc24;
import static strategy.game.version.testutil.TestLocations.loc33;
import static strategy.game.version.testutil.TestLocations.loc34;
import static strategy.game.version.testutil.TestLocations.loc35;
import static strategy.game.version.testutil.TestLocations.loc40;
import static strategy.game.version.testutil.TestLocations.loc41;
import static strategy.game.version.testutil.TestLocations.loc42;
import static strategy.game.version.testutil.TestLocations.loc43;
import static strategy.game.version.testutil.TestLocations.loc44;
import static strategy.game.version.testutil.TestLocations.loc45;
import static strategy.game.version.testutil.TestLocations.loc46;
import static strategy.game.version.testutil.TestLocations.loc51;
import static strategy.game.version.testutil.TestLocations.loc53;
import static strategy.game.version.testutil.TestLocations.loc54;
import static strategy.game.version.testutil.TestLocations.loc64;
import static strategy.game.version.testutil.TestLocations.loc81;
import static strategy.game.version.testutil.TestLocations.loc83;
import static strategy.game.version.testutil.TestLocations.loc84;
import static strategy.game.version.testutil.TestLocations.loc85;
import static strategy.game.version.testutil.TestLocations.loc86;
import static strategy.game.version.testutil.TestLocations.loc93;
import static strategy.game.version.testutil.TestLocations.loc94;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import strategy.common.StrategyException;
import strategy.game.StrategyGameController;
import strategy.game.StrategyGameFactory;
import strategy.game.common.Location2D;
import strategy.game.common.MoveResult;
import strategy.game.common.MoveResultStatus;
import strategy.game.common.Piece;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;
import strategy.game.version.testutil.MockDeltaStrategyController;
import strategy.game.version.testutil.TestConfigurationFactory;

/**
 * Test file for DeltaStrategy
 * @author cbotaish, drob
 * @version 9/26/13
 */
public class DeltaStrategyTest {
	private final StrategyGameFactory factory = StrategyGameFactory.getInstance();
	private List<PieceLocationDescriptor> redPieces;
	private List<PieceLocationDescriptor> bluePieces;
	private StrategyGameController game;

	@Before
	public void setup() throws StrategyException{
		redPieces = TestConfigurationFactory.getInstance().getRedDeltaConfiguration();
		bluePieces = TestConfigurationFactory.getInstance().getBlueDeltaConfiguration();
		game = factory.makeDeltaStrategyGame(redPieces, bluePieces);
	}
	
	@Test
	(expected=StrategyException.class)
	public void nullConfigTest() throws StrategyException
	{
		factory.makeDeltaStrategyGame(null,null);
	}
	
	@Test
	public void validConfigTest() throws StrategyException
	{
		assertNotNull(game);
	}

	@Test
	public void startGameTest() throws StrategyException
	{
		assertNotNull(game);
		game.startGame();
	}
	
	@Test
	(expected=StrategyException.class)
	public void startGameTwice() throws StrategyException
	{
		game.startGame();
		game.startGame();
	}
	
	@Test
	(expected=StrategyException.class)
	public void moveFlag() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.FLAG, loc03, loc04);
	}
	
	@Test
	public void validMove() throws StrategyException
	{
		game.startGame();
		MoveResult result = game.move(PieceType.SERGEANT, loc13, loc14);
		assertEquals(MoveResultStatus.OK, result.getStatus());
	}
	
	@Test(expected=StrategyException.class)
	public void notEnoughStartingRedPieces() throws StrategyException
	{
		redPieces.remove(5);
		factory.makeDeltaStrategyGame(redPieces, bluePieces);
	}
	
	@Test(expected=StrategyException.class)
	public void tooManyBluePieces() throws StrategyException
	{
		redPieces.add(new PieceLocationDescriptor(new Piece(SERGEANT, BLUE), loc03));
		factory.makeDeltaStrategyGame(redPieces, bluePieces);
	}
	
	@Test(expected=StrategyException.class)
	public void bluePieceOnRedSide() throws StrategyException{
		redPieces.remove(1);
		bluePieces.add(new PieceLocationDescriptor(new Piece(MARSHAL,BLUE), loc01));
		factory.makeDeltaStrategyGame(redPieces, bluePieces);
	}
	
	@Test(expected=StrategyException.class)
	public void placeRedPieceInvalidYCoordinate() throws StrategyException
	{
		redPieces.remove(1);
		redPieces.add(new PieceLocationDescriptor(new Piece(MARSHAL, RED), badLoc));
		factory.makeDeltaStrategyGame(redPieces, bluePieces);
	}
	
	@Test(expected=StrategyException.class)
	public void placeRedPieceInvalidXCoordinate() throws StrategyException
	{
		redPieces.remove(1);
		redPieces.add(new PieceLocationDescriptor(new Piece(MARSHAL, RED), new Location2D(-1, 0)));
		factory.makeDeltaStrategyGame(redPieces, bluePieces);
	}
	
	@Test(expected=StrategyException.class)
	public void placeBluePieceInvalidStartCoordinate() throws StrategyException
	{
		bluePieces.remove(11);
		bluePieces.add(new PieceLocationDescriptor(new Piece(SERGEANT, BLUE), loc02));
		factory.makeDeltaStrategyGame(redPieces, bluePieces);
	}
	
	@Test(expected=StrategyException.class)
	public void placeBluePieceOtherInvalidStartCoordinate() throws StrategyException
	{
		bluePieces.remove(11);
		bluePieces.add(new PieceLocationDescriptor(new Piece(SERGEANT, BLUE), loc64));
		factory.makeDeltaStrategyGame(redPieces, bluePieces);
	}
	
	@Test(expected=StrategyException.class)
	public void badConfigurationWithOverlappingPiece() throws StrategyException{
		bluePieces.remove(0);
 		bluePieces.add(new PieceLocationDescriptor(new Piece(SERGEANT, BLUE), loc04));
 		factory.makeDeltaStrategyGame(redPieces, bluePieces);
		fail();
	}
	
	@Test(expected=StrategyException.class)
	public void invalidConfigNotEnoughOfPieceType() throws StrategyException{
		bluePieces.remove(0);
		bluePieces.add(new PieceLocationDescriptor(new Piece(SERGEANT, BLUE), loc04));
 		factory.makeDeltaStrategyGame(redPieces, bluePieces);
		fail();
	}
	
	@Test
	(expected=StrategyException.class)
	public void makeValidMoveBeforeGameStarts() throws StrategyException
	{		
		game.move(SERGEANT, new Location2D(4,1), loc42);
	}
	
	@Test
	public void getPieceAtTest() throws StrategyException
	{
		assertEquals(new Piece(SCOUT, RED), game.getPieceAt(loc51));
	}

	@Test
	public void makeValidMove() throws StrategyException
	{
		game.startGame();
		
		game.move(SERGEANT, loc13, loc14);
	}
	
	@Test
	(expected=StrategyException.class)
	public void makeInvalidMoveFromOffBoardNegativeY() throws StrategyException
	{
		game.startGame();
		
		game.move(MARSHAL, new Location2D(0,-1), loc00);
	}
	
	@Test
	(expected=StrategyException.class)
	public void makeInvalidMoveFromOffBoardNegativeX() throws StrategyException
	{
		game.startGame();
		
		game.move(MARSHAL, new Location2D(-1,0), loc00);
	}
	
	@Test
	(expected=StrategyException.class)
	public void makeInvalidMoveFromOffBoardOverX() throws StrategyException
	{
		game.startGame();
		
		game.move(MARSHAL, new Location2D(10,0), loc00);
	}
	
	@Test
	(expected=StrategyException.class)
	public void makeInvalidMoveFromOffBoardOverY() throws StrategyException
	{
		game.startGame();
		
		game.move(MARSHAL, new Location2D(0,10), loc00);
	}
	
	@Test
	(expected=StrategyException.class)
	public void makeInvalidMoveToOffBoardNegativeY() throws StrategyException
	{
		game.startGame();
		
		game.move(MARSHAL, loc00, new Location2D(0,-1));
	}
	
	@Test
	(expected=StrategyException.class)
	public void makeInvalidMoveToOffBoardNegativeX() throws StrategyException
	{
		game.startGame();
		
		game.move(MARSHAL, loc00, new Location2D(-1,0));
	}
	
	@Test
	(expected=StrategyException.class)
	public void makeInvalidMoveToOffBoardOverX() throws StrategyException
	{
		game.startGame();
		
		game.move(MARSHAL, loc00, new Location2D(10,0));
	}
	
	@Test
	(expected=StrategyException.class)
	public void makeInvalidMoveToOffBoardOverY() throws StrategyException
	{
		game.startGame();
		
		game.move(MARSHAL, loc00, new Location2D(0,10));
	}
	
	@Test
	(expected=StrategyException.class)
	public void makeInvalidMoveOnSelf() throws StrategyException
	{
		game.startGame();
		
		game.move(PieceType.MINER, loc00, loc00);
	}
	
	@Test
	(expected=StrategyException.class)
	public void makeInvalidMoveOnChokepoint() throws StrategyException
	{		
		game.startGame();
		
		game.move(PieceType.SCOUT, loc23, loc24);
	}
	
	@Test
	(expected=StrategyException.class)
	public void makeInvalidMoveMoveTooFar() throws StrategyException
	{		
		game.startGame();
		
		game.move(SERGEANT, loc13, loc15);
	}
	
	@Test
	(expected=StrategyException.class)
	public void makeInvalidMoveMoveDiagonally() throws StrategyException
	{		
		game.startGame();
		
		game.move(SERGEANT, loc13, loc04);
	}
	
	@Test
	(expected=StrategyException.class)
	public void makeInvalidMoveOntoOwnTeam() throws StrategyException
	{		
		game.startGame();
		
		game.move(SERGEANT, loc13, loc03);
	}
	
	@Test
	(expected=StrategyException.class)
	public void makeInvalidMoveDueToMoveRepitition() throws StrategyException
	{		
		game.startGame();
		
		game.move(SERGEANT, loc13, loc14);
		game.move(SERGEANT, loc16, loc15);
		game.move(SERGEANT, loc14, loc13);
		game.move(SERGEANT, loc15, loc16);
		game.move(SERGEANT, loc13, loc14);
	}
	
	@Test
	(expected=StrategyException.class)
	public void makeInvalidMoveMoveFlag() throws StrategyException
	{		
		game.startGame();
		
		game.move(FLAG, loc03, loc04);
	}
	
	@Test
	(expected=StrategyException.class)
	public void makeInvalidMoveOutOfTurn() throws StrategyException
	{		
		game.startGame();
		
		game.move(SERGEANT, loc16, loc15);
	}
	
	@Test
	(expected=StrategyException.class)
	public void makeInvalidMoveWrongPieceType() throws StrategyException
	{		
		game.startGame();
		
		game.move(FLAG, loc13, loc14);
	}
	
	@Test
	(expected=StrategyException.class)
	public void makeInvalidMoveNoPiece() throws StrategyException
	{		
		game.startGame();
		
		game.move(FLAG, loc04, loc05);
	}
			
	@Test
	public void makeValidMoveAlmostRepitition() throws StrategyException
	{		
		game.startGame();
		
		game.move(SERGEANT, loc13, loc14);
		game.move(SERGEANT, loc16, loc15);
		game.move(SERGEANT, loc14, loc13);
		game.move(SERGEANT, loc15, loc16);
		game.move(MARSHAL, loc43, loc44);
		game.move(SPY, loc46, loc45);
		game.move(SERGEANT, loc13, loc14);
	}
	
	@Test
	public void redWinABattle() throws StrategyException
	{		
		game.startGame();
		
		game.move(SERGEANT, loc13, loc14);
		game.move(SERGEANT, loc16, loc15);
		game.move(SERGEANT, loc14, loc04);
		game.move(SERGEANT, loc15, loc14);
		game.move(LIEUTENANT, loc12, loc13);
		game.move(SCOUT, loc86, loc85);
		MoveResult result = game.move(LIEUTENANT, loc13, loc14);

		assertEquals(MoveResultStatus.OK, result.getStatus());
		assertEquals(new PieceLocationDescriptor(new Piece(LIEUTENANT, RED), loc14), result.getBattleWinner());
	}
	
	@Test
	public void redLoseABattle() throws StrategyException
	{		
		game.startGame();
		
		game.move(SERGEANT, loc13, loc14);
		game.move(SERGEANT, loc16, loc15);
		game.move(LIEUTENANT, loc12, loc13);
		game.move(SERGEANT, loc15, loc05);
		game.move(SERGEANT, loc14, loc15);
		game.move(LIEUTENANT, loc17, loc16);
		MoveResult result = game.move(SERGEANT, loc15, loc16);
		
		assertEquals(MoveResultStatus.OK, result.getStatus());
		assertEquals(new PieceLocationDescriptor(new Piece(LIEUTENANT, BLUE), loc15), result.getBattleWinner());
	}

	@Test
	(expected=StrategyException.class)
	public void moveChokePoint() throws StrategyException
	{
		game.startGame();
		
		game.move(CHOKE_POINT, loc24, loc14);
	}
	
	@Test
	(expected=StrategyException.class)
	public void moveBomb() throws StrategyException
	{
		redPieces.remove(0);
		redPieces.remove(1);
		redPieces.add(new PieceLocationDescriptor(new Piece(SERGEANT, RED), loc22));
		redPieces.add(new PieceLocationDescriptor(new Piece(BOMB, RED), loc13));
		
		game = factory.makeDeltaStrategyGame(redPieces, bluePieces);
		
		game.startGame();
		game.move(BOMB, loc13, loc14);
	}
	
	@Test
	public void moveScoutMultipleSpaces() throws StrategyException
	{
		game.startGame();
		game.move(SCOUT, loc83, loc85);
	}
	
	@Test
	(expected=StrategyException.class)
	public void moveScoutMultipleSpacesAndAttack() throws StrategyException
	{
		game.startGame();
		game.move(SCOUT, loc83, loc86);
	}
	
	@Test
	public void scoutMoveHorizontal() throws StrategyException
	{
		MockDeltaStrategyController mockGame = new MockDeltaStrategyController(TestConfigurationFactory.getInstance().getScoutTestConfigurationRed(),TestConfigurationFactory.getInstance().getEmptyConfiguration());
 
		mockGame.startGame();
		mockGame.move(SCOUT, loc41, loc81);
	}
	
	@Test
	public void scoutMoveVerticalBackwards() throws StrategyException
	{
		MockDeltaStrategyController mockGame = new MockDeltaStrategyController(TestConfigurationFactory.getInstance().getScoutTestConfigurationRed(),TestConfigurationFactory.getInstance().getEmptyConfiguration());
 
		mockGame.startGame();
		mockGame.move(SCOUT, loc41, loc40);
	}
	
	@Test
	public void scoutMoveHorizontalBackwars() throws StrategyException
	{
		MockDeltaStrategyController mockGame = new MockDeltaStrategyController(TestConfigurationFactory.getInstance().getScoutTestConfigurationRed(),TestConfigurationFactory.getInstance().getEmptyConfiguration());
 
		mockGame.startGame();
		mockGame.move(SCOUT, loc41, loc01);
	}
	
	@Test
	(expected=StrategyException.class)
	public void scoutMoveOverPieces() throws StrategyException
	{
 
		game.startGame();
		game.move(SCOUT, loc41, loc44);
	}
	
	@Test
	public void BombDoesNotMoveWhenWinner() throws StrategyException
	{
		MockDeltaStrategyController mockGame = new MockDeltaStrategyController(TestConfigurationFactory.getInstance().getBombTestConfigurationRed(),TestConfigurationFactory.getInstance().getBombTestConfigurationBlue());
		mockGame.startGame();
		MoveResult result = mockGame.move(SERGEANT, loc35, loc34);
		
		assertEquals(result.getBattleWinner().getLocation(),loc34); //bomb doesn't move when it blows up a unit
	}
	
	@Test
	public void MarshallDoesNotBeatBomb() throws StrategyException
	{
		MockDeltaStrategyController mockGame = new MockDeltaStrategyController(TestConfigurationFactory.getInstance().getBombTestConfigurationRed(),TestConfigurationFactory.getInstance().getBombTestConfigurationBlue());
		mockGame.startGame();
		MoveResult result = mockGame.move(MARSHAL, loc44, loc34); 
		
		assertEquals(result.getBattleWinner().getPiece().getType(),PieceType.BOMB); //bomb is still there
	}
	
	@Test
	public void MinerDefeatsBomb() throws StrategyException
	{
		MockDeltaStrategyController mockGame = new MockDeltaStrategyController(TestConfigurationFactory.getInstance().getBombTestConfigurationRed(),TestConfigurationFactory.getInstance().getBombTestConfigurationBlue());
		mockGame.startGame();
		MoveResult result = mockGame.move(MINER, loc33, loc34); 
		
		assertEquals(result.getBattleWinner().getPiece().getType(),PieceType.MINER); //bomb doesn't move when it blows up a unit
	}
	
	@Test
	(expected=StrategyException.class)
	public void BombCannotStrike() throws StrategyException {
		MockDeltaStrategyController mockGame = new MockDeltaStrategyController(TestConfigurationFactory.getInstance().getBombTestConfigurationRed(),TestConfigurationFactory.getInstance().getBombTestConfigurationBlue());
		mockGame.startGame();
		mockGame.move(BOMB, loc34, loc33); 
		assert(false);
	}
	
	@Test
	public void spyDefeatsMarshal() throws StrategyException
	{
		game.startGame();
		
		game.move(MARSHAL, loc43, loc44);
		game.move(SPY, loc46, loc45);
		game.move(SPY, loc53, loc54);
		MoveResult result = game.move(SPY, loc45, loc44);
		
		assertEquals(SPY, result.getBattleWinner().getPiece().getType());
		assertEquals(BLUE, result.getBattleWinner().getPiece().getOwner());
		assertEquals(loc44, result.getBattleWinner().getLocation());
		assertEquals(MoveResultStatus.OK, result.getStatus());
	}
	
	@Test
	public void marshalDefeatsSpy() throws StrategyException
	{
		game.startGame();
		
		game.move(MARSHAL, loc43, loc44);
		game.move(SPY, loc46, loc45);
		MoveResult result = game.move(MARSHAL, loc44, loc45);
		
		assertEquals(MARSHAL, result.getBattleWinner().getPiece().getType());
		assertEquals(RED, result.getBattleWinner().getPiece().getOwner());
		assertEquals(loc45, result.getBattleWinner().getLocation());
		assertEquals(MoveResultStatus.OK, result.getStatus());
	}
	
	@Test
	public void bombBeatsOther() throws StrategyException
	{
		redPieces.remove(0);
		redPieces.remove(0);
		redPieces.add(new PieceLocationDescriptor(new Piece(SERGEANT, RED), loc22));
		redPieces.add(new PieceLocationDescriptor(new Piece(BOMB, RED), loc13));
		
		game = factory.makeDeltaStrategyGame(redPieces, bluePieces);
		game.startGame();
		
		game.move(SCOUT, loc83, loc84);
		game.move(SERGEANT, loc16, loc15);
		game.move(SCOUT,loc93, loc94);
		game.move(SERGEANT, loc15, loc14);
		game.move(SCOUT,loc84,loc85);
		MoveResult result = game.move(SERGEANT, loc14, loc13);
		
		assertEquals(BOMB, result.getBattleWinner().getPiece().getType());
		assertEquals(RED, result.getBattleWinner().getPiece().getOwner());
		assertEquals(loc13, result.getBattleWinner().getLocation());
		assertEquals(MoveResultStatus.OK, result.getStatus());
	}
	
	
	@Test
	public void drawWhenOnlyFlagsRemain() throws StrategyException
	{
		MockDeltaStrategyController mockGame = new MockDeltaStrategyController(TestConfigurationFactory.getInstance().getDrawConfigurationRed(),TestConfigurationFactory.getInstance().getDrawConfigurationBlue());
		mockGame.startGame();
		MoveResult result = mockGame.move(SERGEANT, loc33, loc34);
		
		assertEquals(MoveResultStatus.DRAW,result.getStatus());
	}
}
