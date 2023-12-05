import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ApiService {

  constructor(private httpClient: HttpClient) { }

  // Q: What type of data is returned by this link? Could you give an example?
  // A: The data returned is a JSON object. An example of this is:
  // {
  //   "data": [
  //     {
  //       "id": 1,
  //       "title": "The Shawshank Redemption",
  //       "year": 1994,
  //       "runtime": 142,
  //       "genres": [
  //         "Crime",
  //         "Drama"
  //       ],
  //       "director": "Frank Darabont",
  //
  // Q: Can you give an example of the json returned? Make sure it has the attributes title, released, id.
  // A: The JSON returned is:
  // {
  //   "data": [
  //     {
  //       "id": 1,
  //       "title": "The Shawshank Redemption",
  //       "year": 1994,
  //       "runtime": 142,
  //       "genres": [
  //         "Crime",
  //         "Drama"
  //       ],
  //       "director": "Frank Darabont",
  
  fetchData(): Observable<any> {
    return this.httpClient.get('http://127.0.0.1:5001/v1/movies/0');
  }
}
