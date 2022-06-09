from GameStatus import *
from Move import *
from Engine import Engine
import pygame as pg
import pygame_menu as pgm

BOARD_HEIGHT = BOARD_WIDTH = 512
AMOUNT_OF_SQUARES = 8
SIZE_OF_SQUARE = HEIGHT // AMOUNT_OF_SQUARES
BRIGHT_COLOR = (195, 169, 135)
DARK_COLOR = (128, 93, 66)
HIGHLIGHT_COLOR = (255, 255, 102)
MOVE_LOG_AREA_WIDTH = 250
MOVE_LOG_AREA_HEIGHT = BOARD_HEIGHT

'''
This class is responsible for running the app - the whole user interface and interaction with user.
'''


class App:
    def __init__(self):
        pg.init()
        self._win = pg.display.set_mode((BOARD_WIDTH + MOVE_LOG_AREA_WIDTH, BOARD_HEIGHT))
        pg.display.set_caption('Chess')
        self._engine = Engine()

    '''
    Hermetization
    '''
    @property
    def win(self):
        return self._win

    @property
    def engine(self):
        return self._engine

    '''
    This method draws the background of the board - bright and dark squares.
    '''
    def _draw_background(self):
        for row in range(AMOUNT_OF_SQUARES):
            for column in range(AMOUNT_OF_SQUARES):
                if (row + column) % 2 == 0:
                    color = BRIGHT_COLOR
                else:
                    color = DARK_COLOR
                pg.draw.rect(self.win, color,
                             pg.Rect(column * SIZE_OF_SQUARE, row * SIZE_OF_SQUARE, SIZE_OF_SQUARE, SIZE_OF_SQUARE))

    '''
    This method is responsible for highlighting all possible squares where a certain piece can move.
    '''
    def _highlight_squares(self, game_status, correct_moves, square):
        if square != ():
            selected_piece = game_status.board.board[square[0]][square[1]]
            if selected_piece is not None and selected_piece.color == ('w' if game_status.white_turn else 'b'):
                surface = pg.Surface((SIZE_OF_SQUARE, SIZE_OF_SQUARE))
                surface.set_alpha(100)
                surface.fill(pg.Color(HIGHLIGHT_COLOR))
                self.win.blit(surface, (square[1] * SIZE_OF_SQUARE, square[0] * SIZE_OF_SQUARE))
                for move in correct_moves:
                    if move.begin[0] == square[0] and move.begin[1] == square[1]:
                        self.win.blit(surface, (move.end[1] * SIZE_OF_SQUARE, move.end[0] * SIZE_OF_SQUARE))

    '''
    This method is responsible for drawing the move log (list of moves that have been made).
    '''
    def _draw_moves(self, game_status, font):
        area = pg.Rect(BOARD_WIDTH, 0, MOVE_LOG_AREA_WIDTH, MOVE_LOG_AREA_HEIGHT)
        pg.draw.rect(self.win, pg.Color('black'), area)
        moves_in_row = 3
        padding = 5
        spacing = 2
        text_gap = padding
        move_texts = []
        for i in range(0, len(game_status.move_log), 2):
            move_text = str(i // 2 + 1) + '. ' + game_status.move_log[i] + ' '
            if i + 1 < len(game_status.move_log):
                move_text += game_status.move_log[i + 1] + ' '
            move_texts.append(move_text)
        for i in range(0, len(game_status.move_log), moves_in_row):
            text = ''
            for j in range(moves_in_row):
                if i + j < len(move_texts):
                    text += move_texts[i + j]
            text_object = font.render(text, True, pg.Color('white'))
            text_location = area.move(padding, text_gap)
            self.win.blit(text_object, text_location)
            text_gap += text_object.get_height() + spacing

    '''
    This method draws text at the end of the game (depends on what is the final state of the game).
    '''
    def _draw_end_text(self, text):
        font = pg.font.SysFont("Helvetica", 32, True, False)
        text_object = font.render(text, False, pg.Color("gray"))
        text_location = pg.Rect(0, 0, BOARD_WIDTH, BOARD_HEIGHT).move(BOARD_WIDTH / 2 - text_object.get_width() / 2,
                                                                     BOARD_HEIGHT / 2 - text_object.get_height() / 2)
        self.win.blit(text_object, text_location)
        text_object = font.render(text, False, pg.Color('black'))
        self.win.blit(text_object, text_location.move(2, 2))

    '''
    This method is responsible for playing the game - supports user (human or engine) that is making moves and 
    validates the user clicks (if it is a human), ends the game if it is checkmate or stalemate.
    '''
    def _play_game(self, white_player=True, black_player=True, engine=False):
        pg.display.set_caption('Chess')
        self.win.fill(pg.Color(BRIGHT_COLOR))
        game_status = GameStatus()
        correct_moves = game_status.board.get_correct_moves(game_status.white_turn)
        correct_move_made = False
        finish = False
        last_click = ()
        clicks = []

        while not finish:
            human_turn = (game_status.white_turn and white_player) or (not game_status.white_turn and black_player)
            for event in pg.event.get():
                if event.type == pg.QUIT:
                    finish = True
                elif event.type == pg.MOUSEBUTTONDOWN:
                    if game_status.board.game_code == 0 and human_turn:
                        mouse_loc = pg.mouse.get_pos()
                        row = mouse_loc[1] // SIZE_OF_SQUARE
                        column = mouse_loc[0] // SIZE_OF_SQUARE
                        # print(row, column)
                        if last_click == (row, column) or column >= 8:
                            last_click = ()
                            clicks = []
                        else:
                            last_click = (row, column)
                            clicks.append(last_click)
                        if len(clicks) == 2:
                            move = Move(game_status.board.board, clicks[0], clicks[1])
                            for i in range(len(correct_moves)):
                                if move == correct_moves[i]:
                                    move.enpassant = correct_moves[i].enpassant
                                    move.castle = correct_moves[i].castle
                                    game_status.make_move(move)
                                    correct_move_made = True
                                    last_click = ()
                                    clicks = []
                            if not correct_move_made:
                                clicks = [last_click]
                elif white_player and black_player and event.type == pg.KEYDOWN:
                    if event.key == pg.K_LEFT:
                        game_status.take_back()
                        correct_move_made = True

            if game_status.board.game_code == 0 and not human_turn:
                if not engine:
                    move = self.engine.find_random_move(correct_moves)
                else:
                    move = self.engine.find_best_move(game_status.board, game_status.move_log)
                for i in range(len(correct_moves)):
                    if move == correct_moves[i]:
                        move.enpassant = correct_moves[i].enpassant
                        move.castle = correct_moves[i].castle
                game_status.make_move(move)
                correct_move_made = True

            if correct_move_made:
                correct_moves = game_status.board.get_correct_moves(game_status.white_turn)
                # if game_status.board.game_code != 0:
                #     finish = True
                correct_move_made = False

            self._draw_background()
            self._highlight_squares(game_status, correct_moves, last_click)
            game_status.board.draw_board(self.win)

            if game_status.board.game_code == 0:
                self._draw_moves(game_status, pg.font.SysFont("Arial", 12, True, False))

            if game_status.board.game_code == 1:
                if game_status.white_turn:
                    self._draw_end_text('Checkmate - black wins')
                else:
                    self._draw_end_text('Checkmate - white wins')

            elif game_status.board.game_code == -1:
                self._draw_end_text("Stalemate - it's a draw")

            pg.display.flip()

    '''
    This method creates main menu and buttons on it.
    '''
    def _show_main_menu(self):
        menu = pgm.Menu('Chess', 400, 300, theme=pgm.themes.THEME_BLUE)

        menu.add.button('Play', self._show_play_menu)
        menu.add.button('Quit', pgm.events.EXIT)
        menu.mainloop(self.win)

    '''
    This method creates 'play' menu and buttons on it.
    '''
    def _show_play_menu(self):
        menu = pgm.Menu('Choose options', 400, 300, theme=pgm.themes.THEME_BLUE)

        menu.add.button('Play 1 v 1', self._play_game)
        menu.add.button('Play with computer', self._show_computer_menu)
        menu.add.button('Go back', self._show_main_menu)
        menu.add.button('Quit', pgm.events.EXIT)
        menu.mainloop(self.win)

    '''
    This method creates 'play with computer' menu and buttons on it.
    '''
    def _show_computer_menu(self):
        menu = pgm.Menu('Choose side', 400, 300, theme=pgm.themes.THEME_BLUE)

        menu.add.button('White', lambda: self._play_game(black_player=False, engine=True))
        menu.add.button('Black', lambda: self._play_game(white_player=False, engine=True))
        menu.add.button('Go back', self._show_play_menu)
        menu.add.button('Quit', pgm.events.EXIT)
        menu.mainloop(self.win)

    '''
    This method runs the whole application.
    '''
    def run(self):
        self._show_main_menu()

