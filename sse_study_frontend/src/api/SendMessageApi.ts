import { axiosInstance } from "../utils/apiConfig.tsx";

export const SendMessageApi = async (targetMemberId: number): Promise<string | undefined> => {
    try {
        const response = await axiosInstance.post(
            `${process.env.REACT_APP_API_BASE_URL}/send/${targetMemberId}`
        );
        return response.data;
    } catch (error) {
        console.error("전송 실패:", error);
        throw new Error(error.response?.data?.message || "메시지 전송에 실패했습니다.");
    }
}

export const SendAllMessageApi = async (): Promise<string | undefined> => {
    try {
        const response = await axiosInstance.post(
            `${process.env.REACT_APP_API_BASE_URL}/send/all`
        );
        return response.data;
    } catch (error) {
        console.error("전송 실패:", error);
        throw new Error(error.response?.data?.message || "메시지 전송에 실패했습니다.");
    }
}