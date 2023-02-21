import { useEffect } from "react";
import { User } from "../constants/types";
import { useLocalStorage } from "./useLocalStorage";
import { useUser } from "./useUser";

export const useAuth = () => {
    const { user, addUser, removeUser } = useUser();
    const { getItem } = useLocalStorage();
  
    useEffect(() => {
      const user = getItem('user');
      if (user) {
        addUser(JSON.parse(user));
      }
    }, []);
  
    const login = (user: User) => {
      addUser(user);
    };
  
    const logout = () => {
      removeUser();
    };

    const logged = () =>{
      return user
    }
  
    return { user, login, logout, logged };
  };