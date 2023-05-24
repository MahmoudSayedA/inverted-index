# Inverted index

This project is a simple information retrieval system that uses a positional index and cosine similarity to retrieve relevant results based on a user query. The system can index local files and web pages, and return a list of the files or pages that contain the search term, along with their cosine similarity to the query.

## Getting Started

To run the project, you will need to have Java installed on your machine. You can download and install Java from the official Java website.

Once you have Java installed, you can run the project by executing the main() method in the App class. You will be prompted to select whether you want to test the system on local files or on web pages.

## Usage

Indexing Local Files
To index local files, select option 1 when prompted. You will be asked to enter a word to search for. The system will then build an inverted index of all the local filesin the test folder, and return a list of the files that contain the search term, along with their cosine similarity to the query.

## Crawling Web Pages

To crawl web pages, select option 2 when prompted. You will be asked to enter a query to search for. The system will then crawl the web pages on the specified domain, and return a list of the pages that contain the search term, along with their cosine similarity to the query.

## Viewing Results

The system will display the results in descending order of cosine similarity, with the most relevant results at the top. Each result will be displayed with its corresponding cosine similarity score.

## Contributing

Contributions are welcome! If you find a bug or want to add a new feature, feel free toopen an issue or submit a pull request. Before submitting a pull request, please make sure that your changes are well-tested and documented.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE)file for details.
