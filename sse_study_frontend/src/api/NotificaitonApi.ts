import { Notifications } from "../types/Notification.ts";
import { axiosInstance } from "../utils/apiConfig.tsx";

export const NotificationsApi = async (): Promise<Notifications | undefined> => {
    try {
        const response = await axiosInstance.get(
            `${process.env.REACT_APP_API_BASE_URL}/notifications`
        );

        return response.data.notifications as Notifications;
    } catch (error) {
        console.error(error);
        return undefined;
    }
}