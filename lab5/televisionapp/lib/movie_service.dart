import 'dart:convert';
import 'package:http/http.dart' as http;
import 'movie.dart';

class MovieService {
  static const _baseUrl = 'https://api.themoviedb.org/3';
  static const _apiKey = '85bfe250e79f708f9a2d4865e91da4c4';

  static Future<List<Movie>> getPopularMovies() async {
    try {
      final response = await http.get(
        Uri.parse(
            '$_baseUrl/discover/movie?api_key=$_apiKey&include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc'),
      );

      if (response.statusCode == 200) {
        final List<dynamic> data = json.decode(response.body)['results'];
        return data.map((json) => Movie.fromJson(json)).toList();
      } else {
        throw Exception(
            'Failed to load popular movies: ${response.statusCode}');
      }
    } catch (error) {
      throw Exception('Failed to load popular movies: $error');
    }
  }

  static Future<List<Movie>> getSimilarMovies(int movieId) async {
    try {
      final response = await http.get(
        Uri.parse(
          '$_baseUrl/movie/$movieId/similar?api_key=$_apiKey&language=en-US&page=1',
        ),
      );

      if (response.statusCode == 200) {
        final List<dynamic> data = json.decode(response.body)['results'];
        return data.map((json) => Movie.fromJson(json)).toList();
      } else {
        throw Exception(
            'Failed to load similar movies: ${response.statusCode}');
      }
    } catch (error) {
      throw Exception('Failed to load similar movies: $error');
    }
  }
}
