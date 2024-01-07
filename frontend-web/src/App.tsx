import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Login from "./page/login/Login";
import Book from "./page/book/Book";
import { AuthProvider } from "./context/AuthProvider";

function App() {
  const routes = createBrowserRouter([
    {
      path : "/",
      element : <Login/>
    },
    {
      path : "/library",
      element : <Book/>
    }
  ])

  return (
    <AuthProvider>
      <RouterProvider router={routes}/> 
    </AuthProvider>
  );
}

export default App;
