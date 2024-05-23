import 'package:flutter/material.dart';
import 'movie_service.dart';
import 'movie.dart';
import 'movie_tile.dart';

class MovieList extends StatefulWidget {
  const MovieList({super.key});

  @override
  _MovieListState createState() => _MovieListState();
}

class _MovieListState extends State<MovieList> {
  List<Movie> _movies = [];
  List<Movie> _filteredMovies = [];
  bool _isLoading = true;
  String _searchQuery = '';

  @override
  void initState() {
    super.initState();
    _fetchMovies();
  }

  Future<void> _fetchMovies() async {
    final movies = await MovieService.getPopularMovies();
    setState(() {
      _movies = movies;
      _filteredMovies = movies;
      _isLoading = false;
    });
  }

  void _filterMovies(String query) {
    final filteredMovies = _movies.where((movie) {
      return movie.title.toLowerCase().contains(query.toLowerCase());
    }).toList();
    setState(() {
      _filteredMovies = filteredMovies;
      _searchQuery = query;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _isLoading
          ? const Center(child: CircularProgressIndicator())
          : Container(
              color: Colors.black,
              child: FocusTraversalGroup(
                policy: ReadingOrderTraversalPolicy(),
                child: GridView.builder(
                  gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                    crossAxisCount: 4, // Number of columns for TV screen
                    childAspectRatio: 0.7, // Adjusted aspect ratio for TV screen
                    crossAxisSpacing: 16.0, // Spacing between columns
                    mainAxisSpacing: 16.0, // Spacing between rows
                  ),
                  padding: const EdgeInsets.all(16.0), // Padding around the grid
                  itemCount: _filteredMovies.length,
                  itemBuilder: (context, index) {
                    final movie = _filteredMovies[index];
                    return Focus(
                      child: MovieTile(movie: movie),
                    );
                  },
                ),
              ),
            ),
    );
  }
}
