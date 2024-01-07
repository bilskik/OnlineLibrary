import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Login from "./page/login/Login";
import Book from "./page/book/Book";
import { AuthProvider } from "./context/AuthProvider";
import { UserSettingsContext, UserSettingsProvider } from "./context/UserSettingsProvider";
import { useContext } from "react";


function App() {
  const { callAfterRefresh } = useContext(UserSettingsContext);

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

  callAfterRefresh()

  return (
    <AuthProvider>
        <RouterProvider router={routes}/>
    </AuthProvider>
  );
}

export default App;
