import random
from Move import *
from stockfish import Stockfish

'''
This class represents a chess engine - connects with API of Stockfish (one of the strongest chess engines).
'''


class Engine:
    def __init__(self):
        self._stockfish = Stockfish(r'stockfish_15_x64_avx2.exe')
        self.stockfish.update_engine_parameters({"Hash": 2048})
        
    '''
    Hermetization
    '''
    @property
    def stockfish(self):
        return self._stockfish

    '''
    This method returns a random move from the list of moves passed as an argument.
    '''
    @staticmethod
    def find_random_move(moves):
        return moves[random.randint(0, len(moves)-1)]

    '''
    This method uses Stockfish API to find the best move in a current position.
    '''
    def find_best_move(self, board, move_list):
        self.stockfish.set_position(move_list)
        move = self.stockfish.get_best_move()

        f_part = move[0:2]
        s_part = move[2:4]
        begin = (Move.ranks_to_rows[f_part[1]], Move.files_to_cols[f_part[0]])
        end = (Move.ranks_to_rows[s_part[1]], Move.files_to_cols[s_part[0]])

        return Move(board.board, begin, end)
