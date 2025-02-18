import axios from "axios";
import { MemberInfo, MembersInfo } from "../types/MemberInfo.ts";
import { axiosInstance } from "../utils/apiConfig.tsx";

export const MemberRegisterApi = async (name: string, pwd: string): Promise<boolean> => {
    try {
        const response = await axios.post(
            `${process.env.REACT_APP_API_BASE_URL}`,
            { name, pwd }
        );

        if (response.status === 200) {
            return true;
        }
    } catch (error) {
        console.error(error);
    }

    return false;
};

export const MemberLoginApi = async (name: string, pwd: string): Promise<MemberInfo | undefined> => {
    try {
        const response = await axios.post(`${process.env.REACT_APP_API_BASE_URL}/members/login`,
            { name, pwd }
        );

        if (response.status === 200) {
            localStorage.setItem("accessToken", response.data.accessToken);
            return response.data as MemberInfo;
        }
    } catch (error) {
        console.error(error);
    }
};

export const MemberInfoApi = async (): Promise<MemberInfo | undefined> => {
    try {
        const response = await axiosInstance.get(`${process.env.REACT_APP_API_BASE_URL}/members/info`);

        if (response.status === 200) {
            return response.data as MemberInfo;
        }
    } catch (error) {
        console.error(error);
    }
}

export const MemberListApi = async (): Promise<MembersInfo | undefined> => {
    try {
        const response = await axiosInstance.get(`${process.env.REACT_APP_API_BASE_URL}/members`);

        if (response.status === 200) {
            return response.data.membersInfoResDtos as MembersInfo;
        }
    } catch (error) {
        console.error(error);
    }
}

export const MemberLogoutApi = async (): Promise<boolean | undefined> => {
    try {
        const response = await axiosInstance.delete(`${process.env.REACT_APP_API_BASE_URL}/members/logout`);

        if (response.status === 204) {
            localStorage.removeItem("accessToken");
            return true;
        }
    } catch (error) {
        console.error(error);
    }
}