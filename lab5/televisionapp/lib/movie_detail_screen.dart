import 'package:flutter/material.dart';
import 'movie.dart';
import 'movie_service.dart';

class MovieDetailScreen extends StatelessWidget {
  final Movie movie;

  const MovieDetailScreen({super.key, required this.movie});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
          movie.title,
          style: const TextStyle(fontWeight: FontWeight.bold),
        ),
      ),
      body: Stack(
        children: [
          Positioned.fill(
            child: Image.network(
              'https://image.tmdb.org/t/p/w500${movie.posterPath}',
              fit: BoxFit.cover,
              color: Colors.black.withOpacity(0.5),
              colorBlendMode: BlendMode.darken,
            ),
          ),
          FocusTraversalGroup(
            policy: ReadingOrderTraversalPolicy(),
            child: SingleChildScrollView(
              padding: const EdgeInsets.all(16),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Row(
                    children: [
                      Expanded(
                        flex: 1,
                        child: Container(
                          alignment: Alignment.center,
                          child: Image.network(
                            'https://image.tmdb.org/t/p/w500${movie.posterPath}',
                            width: 250,
                            fit: BoxFit.contain,
                          ),
                        ),
                      ),
                      const SizedBox(width: 20),
                      Expanded(
                        flex: 2,
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            const Text(
                              'Overview',
                              style: TextStyle(
                                fontSize: 24,
                                fontWeight: FontWeight.bold,
                                color: Colors.white,
                              ),
                            ),
                            const SizedBox(height: 10),
                            Text(
                              movie.overview,
                              style: const TextStyle(fontSize: 18, color: Colors.white70),
                            ),
                            const SizedBox(height: 20),
                            const Text(
                              'Genres',
                              style: TextStyle(
                                fontSize: 24,
                                fontWeight: FontWeight.bold,
                                color: Colors.white,
                              ),
                            ),
                            const SizedBox(height: 10),
                            const Wrap(
                              spacing: 10,
                              runSpacing: 10,
                            ),
                            const SizedBox(height: 20),
                            const Text(
                              'Release Date',
                              style: TextStyle(
                                fontSize: 24,
                                fontWeight: FontWeight.bold,
                                color: Colors.white,
                              ),
                            ),
                            const SizedBox(height: 10),
                            Text(
                              movie.releaseDate,
                              style: const TextStyle(fontSize: 18, color: Colors.white70),
                            ),
                            const SizedBox(height: 20),
                            const Text(
                              'Rating',
                              style: TextStyle(
                                fontSize: 24,
                                fontWeight: FontWeight.bold,
                                color: Colors.white,
                              ),
                            ),
                            const SizedBox(height: 10),
                            Text(
                              movie.voteAverage.toString(),
                              style: const TextStyle(fontSize: 18, color: Colors.white70),
                            ),
                            const SizedBox(height: 20),
                            ElevatedButton.icon(
                              onPressed: () {
                                // Add functionality to play the trailer
                              },
                              icon: Icon(Icons.play_arrow),
                              label: Text('Play Trailer'),
                              style: ElevatedButton.styleFrom(
                                foregroundColor: Colors.white, 
                                backgroundColor: Colors.red,
                              ),
                            ),
                          ],
                        ),
                      ),
                    ],
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }
}
