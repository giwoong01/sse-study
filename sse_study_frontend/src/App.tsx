import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Home from "./pages/Home.tsx";
import { SSEProvider } from "./contexts/SSEProvider.tsx";
import LoginPage from "./pages/LoginPage.tsx";
import Notification from "./pages/Notification.tsx";
import RegisterPage from "./pages/RegisterPage.tsx";
import { ToastContainer } from "react-toastify";

const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LoginPage />}></Route>
        <Route path="/register" element={<RegisterPage />}></Route>

        <Route
          path="/*"
          element={
            <SSEProvider url={""}>
              <Routes>
                <Route path="/home" element={<Home />} />
                <Route path="/notification" element={<Notification />} />
              </Routes>
              <ToastContainer position="top-left" />
            </SSEProvider>
          }
        ></Route>
      </Routes>
    </BrowserRouter>
  );
};

export default App;
