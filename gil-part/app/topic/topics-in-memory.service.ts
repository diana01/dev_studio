import { InMemoryDbService } from 'angular-in-memory-web-api';

export class InMemoryDataService implements InMemoryDbService {
    createDb() {
        let topics = [
            {id: 0, name: 'Topic 0'}

        ];
        return {topics};
    }

}