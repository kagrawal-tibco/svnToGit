
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { RestService } from '../core/rest.service';
import { ScmRepositoryRecord, } from '../models/dto';
import { Response } from '../models/response';
/**
 * @author Samuel Moore (samoore@tibco.com)
 */
@Injectable()
export class SCMService {

    constructor(
        protected rest: RestService,
    ) {
    }

    /**
     * Retrieves the configured source control management repositories.
     */
    listRepositories(): Observable<ScmRepositoryRecord[]> {
        return this.rest.get('/scm/repositories').pipe(map(response =>
            response.record.reduce((records, record) => {
                records.push(<ScmRepositoryRecord>record);
                return records;
            }, [])
        ));
    }

    /**
     * Retrieves the directory and file entries within a directory and its subdirectories of the Subvserion or Git repository sandbox.
     * @param repositoryName The name of the repository to enumerate.
     * @param startDirectory The relative directory within the sandbox to start the enumeration.
     *                       By default, the enumeration starts at the root of the sandbox.
     * @param maxDepth The maximum depth to traverse.
     * @param maxReturnValues The maximum number of values to return.
     */
    enumerateRepository(repositoryName: string, startDirectory?: string, maxDepth?: Number, maxReturnValues?: Number):
        Observable<Response> {
        return this.rest.get('/scm/'.concat(repositoryName).concat('/enumerate'),
            {
                params: {
                    'start': startDirectory,
                    'maxDepth': maxDepth,
                    'maxReturnValues': maxReturnValues
                }
            });
    }

    /**
     * Imports a project from a specified source control management repository into the specified AMS project.
     * @param repository The name of the configured scm repository to import from.
     * @param project The name of the AMS project to import the artifacts into.
     * @param projectPath The path inside the source control management repository to consider the "root"
     * @param filePaths Array of paths relative to the project path.
     * @param comment
     * @param description
     */
    importProject(repository: string, project: string, projectPath?: string, filePaths?: string[], comment?: string, description?: string):
        Promise<Response> {
        return this.rest.post('/scm/'.concat(repository).concat('/').concat(project).concat('/import'),
            {
                'comment': comment,
                'description': description,
                'projectPath': projectPath,
                'filePaths': filePaths
            }).toPromise();
    }

    /**
     * Forces a pull from the specified project's repository.
     * @param project Name of the repository to pull from.
     */
    forcePull(project: string): Promise<Response> {
        return this.projectToRepository(project)
            .then((response: Response) => {
                return this.rest.get('/scm/'.concat(response.record[0].name).concat('/pull'), {
                    params: {
                        'repositoryName': response.record[0].name
                    }
                }).toPromise();
            });
    }

    /**
     * Given an AMS project name, this api will return the name of its originating repository.
     * @param project AMS Project name that is to be mapped to its respective repository name.
     */
    projectToRepository(project: string): Promise<Response> {
        return this.rest.get('/scm/repositories', {
            params: {
                'projectName': project,
            }
        }).toPromise();
    }
}
