class Genre {
  final int id;
  final String name;

  Genre({
    required this.id,
    required this.name,
  });

  factory Genre.fromJson(Map<String, dynamic> json) {
    return Genre(
      id: json['id'],
      name: json['name'],
    );
  }
}

class Movie {
  final int id;
  final String title;
  final String overview;
  final String posterPath;
  final double voteAverage;
  final double popularity;
  final String originalLanguage;
  final String originalTitle;
  final String releaseDate;
  final bool adult;

  Movie({
    required this.id,
    required this.title,
    required this.overview,
    required this.posterPath,
    required this.voteAverage,
    required this.popularity,
    required this.originalLanguage,
    required this.originalTitle,
    required this.releaseDate,
    required this.adult,
  });

  factory Movie.fromJson(Map<String, dynamic> json) {
    return Movie(
      id: json['id'],
      title: json['title'],
      overview: json['overview'],
      posterPath: json['poster_path'],
      voteAverage: (json['vote_average'] ?? 0.0).toDouble(),
      popularity: (json['popularity'] ?? 0.0).toDouble(),
      originalLanguage: json['original_language'],
      originalTitle: json['original_title'],
      releaseDate: json['release_date'],
      adult: json['adult'],
    );
  }
}
