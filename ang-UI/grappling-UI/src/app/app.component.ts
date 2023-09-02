import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  data: any;

  title = 'grappling-UI';
  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    console.log("Just an update to know we're working")
    this.http.get('http://127.0.0.1:5001/v1/movies/0').subscribe(
      (response) => {
        this.data = response;
        console.log(this.data);
      },
      (error) => {
        console.log(error);
      }
    );
  }
}
